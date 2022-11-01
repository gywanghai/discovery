package com.ocean.discovery.core.route;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 流量标签
 */
public class RouteRequest implements Serializable {
    /**
     * 分组名称
     */
    private String group;
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 路由策略ID
     */
    private String strategyId;
    /**
     * 请求头
     */
    private Map<String, List<String>> headers = Collections.emptyMap();

    public String getGroup() {
        return group;
    }

    public RouteRequest setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public RouteRequest setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public RouteRequest setStrategyId(String strategyId) {
        this.strategyId = strategyId;
        return this;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public RouteRequest setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RouteRequest.class.getSimpleName() + "[", "]")
                .add("group='" + group + "'")
                .add("serviceName='" + serviceName + "'")
                .add("strategyId='" + strategyId + "'")
                .add("headers=" + headers)
                .toString();
    }
}
