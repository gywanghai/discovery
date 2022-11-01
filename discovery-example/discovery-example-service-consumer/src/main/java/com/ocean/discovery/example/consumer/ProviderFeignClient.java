package com.ocean.discovery.example.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "discovery-service-provider")
public interface ProviderFeignClient {

    @RequestMapping(value = "/provider/hello")
    public String hello();
}
