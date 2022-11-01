package com.ocean.discovery.plugin.springmvc;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.DispatcherServlet;

@ConditionalOnClass(DispatcherServlet.class)
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@Import(DiscoveryWebMvcConfiguration.class)
public class DiscoveryWebMvcAutoConfiguration {

}
