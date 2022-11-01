package com.ocean.discovery.plugin.springmvc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServlet;


@Configuration
@ConditionalOnClass({DispatcherServlet.class, HttpServlet.class})
public class DiscoveryWebMvcConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DiscoveryFilter discoveryFilter(){
        return new DiscoveryFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean<DiscoveryFilter> discoveryFilterRegistrationBean(){
        FilterRegistrationBean<DiscoveryFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(discoveryFilter());
        registrationBean.setName(DiscoveryFilter.class.getSimpleName());
        registrationBean.setOrder(-1);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
