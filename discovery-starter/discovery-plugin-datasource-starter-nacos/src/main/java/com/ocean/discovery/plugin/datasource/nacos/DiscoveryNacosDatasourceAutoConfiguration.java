package com.ocean.discovery.plugin.datasource.nacos;

import com.alibaba.cloud.nacos.NacosConfigAutoConfiguration;
import com.ocean.discovery.plugin.ribbon.DiscoveryRibbonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Import;

/**
 * @author 王海
 */
@AutoConfigureAfter(NacosConfigAutoConfiguration.class)
@AutoConfigureBefore(DiscoveryRibbonAutoConfiguration.class)
@Import({NacosDatasourceProperties.class, DiscoveryNacosDatasourceConfiguration.class})
public class DiscoveryNacosDatasourceAutoConfiguration {

}
