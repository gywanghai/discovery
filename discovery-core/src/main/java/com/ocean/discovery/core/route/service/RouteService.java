package com.ocean.discovery.core.route.service;

import com.ocean.discovery.core.route.RouteRequest;
import com.ocean.discovery.core.rule.RouteResult;

import java.util.List;
import java.util.Map;

public interface RouteService {

    /**
     * 流量染色
     * @return 对流量进行染色
     */
    public RouteRequest flowDye(String group, Map<String, List<String>> headers);

    /**
     * 根据请求计算路由结果
     * @param strategyId 流量标签
     */
    public RouteResult select(String group, String serviceName, String strategyId);
}
