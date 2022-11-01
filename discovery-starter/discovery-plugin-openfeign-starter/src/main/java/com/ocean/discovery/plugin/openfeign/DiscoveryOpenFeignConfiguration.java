package com.ocean.discovery.plugin.openfeign;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscoveryOpenFeignConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DiscoveryRequstInterceptor discoveryRequestInterceptor(){
        return new DiscoveryRequstInterceptor();
    }

}
