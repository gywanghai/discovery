package com.ocean.discovery.example.consumer;

import cn.hutool.core.thread.NamedThreadFactory;
import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;
import com.ocean.discovery.core.constants.DiscoveryConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;

@RestController
@RequestMapping(value = "/consumer")
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProviderFeignClient providerFeignClient;

    ExecutorService executorService = Executors.newSingleThreadExecutor(new NamedThreadFactory("", false));

    @RequestMapping(value = "hello")
    public String hello(@RequestHeader(value = "a", required = false) String a,
                        @RequestHeader(value = DiscoveryConstants.HEADER_GROUP, required = false) String group,
                        @RequestHeader(value = DiscoveryConstants.HEADER_STRATEGYID, required = false) String strategyId){
        return "a=" + a + ", group = " + group + ", strategyId = " + strategyId;
    }

    @RequestMapping(value = "testOpenfeign")
    public String testOpenfeign() throws ExecutionException, InterruptedException {
        Future<String> future = executorService.submit(TtlCallable.get(() -> providerFeignClient.hello()));
        return future.get();
    }

    @RequestMapping(value = "testRibbon")
    public String testRibbon() throws ExecutionException, InterruptedException {
        Future<String> future = executorService.submit(TtlCallable.get(() -> restTemplate.getForObject("http://discovery-service-provider/provider/hello", String.class)));
        return future.get();
    }
}
