package com.ocean.discovery.plugin.registry.nacos;


import com.alibaba.cloud.nacos.registry.NacosRegistrationCustomizer;
import com.ocean.discovery.core.service.ServiceTagExtractor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscoveryRegistryNacosConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ServiceTagExtractor serviceTagExtractor(){
        return new NacosServiceTagExtractor();
    }

    @Bean
    @ConditionalOnMissingBean
    public NacosRegistrationCustomizer nacosRegistrationCustomizer(){
        return new DiscoveryNacosRegistrationCustomizer();
    }

}
