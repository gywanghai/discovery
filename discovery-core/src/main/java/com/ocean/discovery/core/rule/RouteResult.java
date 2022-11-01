package com.ocean.discovery.core.rule;

import java.util.StringJoiner;

public class RouteResult {
    /**
     * 分组名
     */
    private String group;
    /**
     * 服务名
     */
    private String serviceName;
    /**
     * 版本
     */
    private String version;

    public RouteResult(){

    }

    public RouteResult(String group, String serviceName, String version){
        this.group = group;
        this.serviceName = serviceName;
        this.version = version;
    }

    public String getGroup() {
        return group;
    }

    public RouteResult setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public RouteResult setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public RouteResult setVersion(String version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RouteResult.class.getSimpleName() + "[", "]")
                .add("group='" + group + "'")
                .add("serviceName='" + serviceName + "'")
                .add("version='" + version + "'")
                .toString();
    }
}
