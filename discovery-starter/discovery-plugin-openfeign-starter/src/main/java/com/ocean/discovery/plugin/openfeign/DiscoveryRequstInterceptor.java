package com.ocean.discovery.plugin.openfeign;

import com.ocean.discovery.core.constants.DiscoveryConstants;
import com.ocean.discovery.core.context.DiscoveryContext;
import com.ocean.discovery.core.route.RouteRequest;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class DiscoveryRequstInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        RouteRequest routeRequest = DiscoveryContext.getRouteRequest();
        if(routeRequest != null){
            routeRequest.getHeaders().entrySet().stream().forEach(entry -> {
                template.header(entry.getKey(), entry.getValue());
            });
            template.header(DiscoveryConstants.HEADER_GROUP, routeRequest.getGroup());
            template.header(DiscoveryConstants.HEADER_STRATEGYID, routeRequest.getStrategyId());
        }
    }
}
