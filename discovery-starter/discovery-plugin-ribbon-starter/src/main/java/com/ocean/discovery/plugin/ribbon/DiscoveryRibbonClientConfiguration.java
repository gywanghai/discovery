package com.ocean.discovery.plugin.ribbon;

import com.netflix.ribbon.Ribbon;
import com.ocean.discovery.core.route.service.RouteService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 王海
 */
@Configuration
public class DiscoveryRibbonClientConfiguration {

    @Bean
    @ConditionalOnClass(Ribbon.class)
    public LoadBalancerRequestTransformer loadBalancerRequestTransformer(){
        return new DiscoveryLoadBalancerRequestTransformer();
    }
}
