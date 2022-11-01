package com.ocean.discovery.plugin.springmvc;

import com.ocean.discovery.core.constants.DiscoveryConstants;
import com.ocean.discovery.core.context.DiscoveryContext;
import com.ocean.discovery.core.route.RouteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
public class DiscoveryFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(DiscoveryFilter.class);

    @Autowired
    private Environment environment;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setServiceName(environment.getProperty("spring.application.name"));
        Enumeration<String> headerNameEnumeration = request.getHeaderNames();
        List<String> headerNames = new ArrayList<>();
        while (headerNameEnumeration.hasMoreElements()){
            headerNames.add(headerNameEnumeration.nextElement());
        }
        Map<String, List<String>> headerMap = new HashMap<>();
        headerNames.stream().forEach(headerName -> {
            Enumeration<String> headerValueEnumreation = request.getHeaders(headerName);
            List<String> headerValues = new ArrayList<>();
            while (headerValueEnumreation.hasMoreElements()){
                headerValues.add(headerValueEnumreation.nextElement());
            }
            headerMap.put(headerName, headerValues);

            if(headerName.equals(DiscoveryConstants.HEADER_GROUP)){
                routeRequest.setGroup(headerValues.get(0));
            }
            if(headerName.equals(DiscoveryConstants.HEADER_STRATEGYID)){
                routeRequest.setStrategyId(headerValues.get(0));
            }
        });
        routeRequest.setHeaders(headerMap);
        DiscoveryContext.setRouteRequest(routeRequest);
        filterChain.doFilter(request, response);
    }
}
