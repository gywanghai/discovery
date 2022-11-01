package com.ocean.discovery.plugin.registry.nacos;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.ocean.discovery.core.service.ServiceTag;
import com.ocean.discovery.core.service.ServiceTagExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author 王海
 */
public class NacosServiceTagExtractor implements ServiceTagExtractor {

    private static final Logger log = LoggerFactory.getLogger(NacosServiceTagExtractor.class);

    @Override
    public ServiceTag extract(String serviceName, Object server) {
        NacosServer nacosServer = (NacosServer) server;
        Map<String,String> metadata = nacosServer.getMetadata();
        if(metadata == null || !metadata.containsKey("version") || !metadata.containsKey("group")){
            log.warn("服务实例[{}:{}:{}] 未设置标签信息", serviceName,
                    nacosServer.getHost(), nacosServer.getPort());
            return null;
        }
        ServiceTag serviceTag = new ServiceTag();
        serviceTag.setServiceName(((NacosServer) server).getInstance().getServiceName());
        serviceTag.setGroup(metadata.get("group"));
        serviceTag.setVersion(metadata.get("version"));
        return serviceTag;
    }
}
