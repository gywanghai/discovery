package com.ocean.discovery.plugin.gateway;

import com.ocean.discovery.plugin.ribbon.DiscoveryRibbonAutoConfiguration;
import com.ocean.discovery.spring.DiscoveryProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Import;

@ConditionalOnClass(GlobalFilter.class)
@AutoConfigureBefore(GatewayAutoConfiguration.class)
@AutoConfigureAfter(DiscoveryRibbonAutoConfiguration.class)
@Import({DiscoveryGatewayConfiguration.class, DiscoveryProperties.class})
public class DiscoveryGatewayAutoConfiguration {

}
