package com.ocean.discovery.plugin.gateway;

import com.ocean.discovery.core.constants.DiscoveryConstants;
import com.ocean.discovery.core.context.DiscoveryContext;
import com.ocean.discovery.core.route.RouteRequest;
import com.ocean.discovery.core.route.service.RouteService;
import com.ocean.discovery.spring.DiscoveryProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流量染色全局过滤器
 */
public class DiscoveryFlowDyeGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RouteService routeService;

    @Autowired
    private DiscoveryProperties discoveryProperties;

    @Override
    public int getOrder() {
        return LoadBalancerClientFilter.LOAD_BALANCER_CLIENT_FILTER_ORDER - 100;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        Map<String, List<String>> headerMap = new HashMap<>();
        headers.entrySet().stream().forEach(entry -> {
            headerMap.put(entry.getKey(), headers.getValuesAsList(entry.getKey()));
        });

        RouteRequest routeRequest = routeService.flowDye(discoveryProperties.getMetadata().getGroup(), headerMap);

        // 流量染色(往下一个 Filter 传递)
        DiscoveryContext.setRouteRequest(routeRequest);
        // 流量染色（往下游服务传递）
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header(DiscoveryConstants.HEADER_GROUP, routeRequest.getGroup())
                .header(DiscoveryConstants.HEADER_STRATEGYID,routeRequest.getStrategyId())
                .build();

        //将现在的request 变成 change对象
        ServerWebExchange serverWebExchange = exchange.mutate().request(request).build();
        return chain.filter(serverWebExchange);
    }
}
