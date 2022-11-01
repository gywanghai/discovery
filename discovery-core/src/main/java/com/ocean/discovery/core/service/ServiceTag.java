package com.ocean.discovery.core.service;

import java.util.StringJoiner;

/**
 * 服务标签
 */
public class ServiceTag {

    private String serviceName;

    private String group;

    private String version;

    public ServiceTag(){

    }

    public ServiceTag(String serviceName, String group, String version){
        this.serviceName = serviceName;
        this.group = group;
        this.version = version;
    }

    public String getServiceName() {
        return serviceName;
    }

    public ServiceTag setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public ServiceTag setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public ServiceTag setVersion(String version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ServiceTag.class.getSimpleName() + "[", "]")
                .add("serviceName='" + serviceName + "'")
                .add("group='" + group + "'")
                .add("version='" + version + "'")
                .toString();
    }
}
