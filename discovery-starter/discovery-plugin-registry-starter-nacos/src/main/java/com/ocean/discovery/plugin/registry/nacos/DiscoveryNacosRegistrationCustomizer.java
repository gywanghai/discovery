package com.ocean.discovery.plugin.registry.nacos;

import com.alibaba.cloud.nacos.registry.NacosRegistration;
import com.alibaba.cloud.nacos.registry.NacosRegistrationCustomizer;
import com.google.common.base.Preconditions;
import com.ocean.discovery.core.vo.Metadata;
import com.ocean.discovery.spring.DiscoveryProperties;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author 王海
 */
public class DiscoveryNacosRegistrationCustomizer implements NacosRegistrationCustomizer {

    @Autowired
    private DiscoveryProperties discoveryProperties;

    @PostConstruct
    public void init(){
        Metadata metadata = discoveryProperties.getMetadata();
        Preconditions.checkArgument(metadata != null, "Discovery Metadata must not be null");
        Preconditions.checkArgument(metadata.getGroup() != null, "Discovery Metadata's group must not be null");
    }

    @Override
    public void customize(NacosRegistration registration) {
        registration.getMetadata().put("group", discoveryProperties.getMetadata().getGroup());
        registration.getMetadata().put("version", discoveryProperties.getMetadata().getVersion());
    }
}
