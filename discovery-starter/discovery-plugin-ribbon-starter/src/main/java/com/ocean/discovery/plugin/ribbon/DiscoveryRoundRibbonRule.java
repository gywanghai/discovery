package com.ocean.discovery.plugin.ribbon;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.Server;
import com.ocean.discovery.core.context.DiscoveryContext;
import com.ocean.discovery.core.route.RouteRequest;
import com.ocean.discovery.core.route.service.RouteService;
import com.ocean.discovery.core.rule.RouteResult;
import com.ocean.discovery.core.service.ServiceTag;
import com.ocean.discovery.core.service.ServiceTagExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 王海
 */
public class DiscoveryRoundRibbonRule extends AbstractLoadBalancerRule {
    private AtomicInteger nextServerCyclicCounter;

    @Autowired
    protected ServiceTagExtractor serviceTagExtractor;

    @Autowired
    protected RouteService routeService;

    private static Logger log = LoggerFactory.getLogger(DiscoveryRoundRibbonRule.class);

    public DiscoveryRoundRibbonRule() {
        nextServerCyclicCounter = new AtomicInteger(0);
    }


    @Override
    public Server choose(Object key) {
        DynamicServerListLoadBalancer lb = (DynamicServerListLoadBalancer) getLoadBalancer();
        if (lb == null) {
            log.warn("no load balancer");
            return null;
        }
        RouteRequest routeRequest = DiscoveryContext.getRouteRequest();
        List<Server> reachableServers = lb.getReachableServers();

        String serviceName = lb.getName();

        // 如果需要对服务器进行标签分组过滤
        if(routeRequest != null){
            reachableServers = filter(serviceName, routeRequest, reachableServers);
        }

        int upCount = reachableServers.size();
        if ((upCount == 0)) {
            log.warn("No up servers available from load balancer: " + lb);
            return null;
        }

        int nextServerIndex = incrementAndGetModulo(upCount);
        Server server = reachableServers.get(nextServerIndex);

        if (server == null) {
            log.warn("No up servers available from load balancer: " + lb);
            return null;
        }
        return server;
    }

    /**
     * Inspired by the implementation of {@link AtomicInteger#incrementAndGet()}.
     *
     * @param modulo The modulo to bound the value of the counter.
     * @return The next value.
     */
    private int incrementAndGetModulo(int modulo) {
        for (;;) {
            int current = nextServerCyclicCounter.get();
            int next = (current + 1) % modulo;
            if (nextServerCyclicCounter.compareAndSet(current, next)){
                return next;
            }
        }
    }

    private List<Server> filter(String serviceName, RouteRequest routeRequest, List<Server> servers) {
        DynamicServerListLoadBalancer loadBalancer = (DynamicServerListLoadBalancer) getLoadBalancer();
        if(StringUtils.isNotBlank(routeRequest.getStrategyId())){
            RouteResult routeResult = routeService.select(routeRequest.getGroup(), loadBalancer.getName(), routeRequest.getStrategyId());
            if(routeResult == null){
                log.warn("未找到标签为 [group: {}] 的[{}]服务实例，取消按标签进行路由", routeRequest.getGroup(),serviceName);
                return servers;
            }
            List<Server> list = servers.stream().filter(server -> {
                ServiceTag serviceTag = serviceTagExtractor.extract(serviceName, server);
                if(serviceTag == null){
                    return false;
                }
                return StringUtils.equals(serviceTag.getGroup(),routeResult.getGroup())
                        && StringUtils.equals(serviceTag.getVersion(), routeResult.getVersion());
            }).collect(Collectors.toList());
            if(list.size() > 0){
                return list;
            }
            log.warn("未找到标签为 [group: {}, version: {}] 的[{}]服务实例，取消按标签进行路由",
                    routeRequest.getGroup(), routeResult.getVersion() ,serviceName);
            return servers;
        }
        return servers;
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        //
    }
}