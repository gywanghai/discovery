package com.ocean.discovery.core.service;

/**
 * 服务标签提取器
 */
public interface ServiceTagExtractor {

    public ServiceTag extract(String serviceName, Object server);

}
