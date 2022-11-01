package com.ocean.discovery.plugin.ribbon;

import com.ocean.discovery.core.constants.DiscoveryConstants;
import com.ocean.discovery.core.context.DiscoveryContext;
import com.ocean.discovery.core.route.RouteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestTransformer;
import org.springframework.http.HttpRequest;

/**
 * 负载均衡请求处理器
 * @author 王海
 */
public class DiscoveryLoadBalancerRequestTransformer implements LoadBalancerRequestTransformer {

    private static final Logger log = LoggerFactory.getLogger(DiscoveryLoadBalancerRequestTransformer.class);

    @Override
    public HttpRequest transformRequest(HttpRequest request, ServiceInstance instance) {
        if(DiscoveryContext.getRouteRequest() == null){
            return request;
        }
        else {
            RouteRequest routeRequest = DiscoveryContext.getRouteRequest();
            routeRequest.getHeaders().entrySet().forEach(entry -> {
                request.getHeaders().addAll(entry.getKey(), entry.getValue());
            });
            request.getHeaders().set(DiscoveryConstants.HEADER_GROUP, routeRequest.getGroup());
            request.getHeaders().set(DiscoveryConstants.HEADER_STRATEGYID, routeRequest.getStrategyId());
        }
        return request;
    }
}
