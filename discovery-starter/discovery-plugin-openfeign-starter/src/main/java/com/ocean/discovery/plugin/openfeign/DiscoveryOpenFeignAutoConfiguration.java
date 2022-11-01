package com.ocean.discovery.plugin.openfeign;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfigureAfter(FeignAutoConfiguration.class)
@Import(DiscoveryOpenFeignConfiguration.class)
public class DiscoveryOpenFeignAutoConfiguration {
}
