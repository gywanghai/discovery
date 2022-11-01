package com.ocean.discovery.plugin.datasource.nacos;

import com.ocean.discovery.core.datasource.RouteRuleDatasource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscoveryNacosDatasourceConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RouteRuleDatasource routeRuleDatasource(){
        return new NacosDatasource();
    }
}
