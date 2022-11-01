package com.ocean.discovery.core.rule;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * 路由规则条目
 */
public class RouteStrategyItem {
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 版本号
     */
    private String version;

    public RouteStrategyItem(){

    }

    public RouteStrategyItem(String serviceName, String version){
        this.serviceName = serviceName;
        this.version = version;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getVersion() {
        return version;
    }

    public RouteStrategyItem setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public RouteStrategyItem setVersion(String version) {
        this.version = version;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RouteStrategyItem routeStrategyItem = (RouteStrategyItem) o;
        return getServiceName().equals(routeStrategyItem.getServiceName()) && getVersion().equals(routeStrategyItem.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getServiceName(), getVersion());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RouteStrategyItem.class.getSimpleName() + "[", "]")
                .add("serviceName='" + serviceName + "'")
                .add("version='" + version + "'")
                .toString();
    }
}
