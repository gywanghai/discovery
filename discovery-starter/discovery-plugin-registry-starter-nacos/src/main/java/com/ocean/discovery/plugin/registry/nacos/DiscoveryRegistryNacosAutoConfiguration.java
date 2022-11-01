package com.ocean.discovery.plugin.registry.nacos;

import com.alibaba.cloud.nacos.registry.NacosServiceRegistryAutoConfiguration;
import com.ocean.discovery.spring.DiscoveryProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Import;

/**
 * @author 王海
 */
@AutoConfigureBefore(NacosServiceRegistryAutoConfiguration.class)
@Import({DiscoveryProperties.class, DiscoveryRegistryNacosConfiguration.class})
public class DiscoveryRegistryNacosAutoConfiguration {

}
