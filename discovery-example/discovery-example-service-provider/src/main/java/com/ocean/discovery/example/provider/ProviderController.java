package com.ocean.discovery.example.provider;

import com.ocean.discovery.core.constants.DiscoveryConstants;
import com.ocean.discovery.core.context.DiscoveryContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.commons.httpclient.ApacheHttpClientFactory;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王海
 */
@RestController
@RequestMapping(value = "/provider")
public class ProviderController {

    private static final Logger log = LoggerFactory.getLogger(ProviderController.class);

    @RequestMapping(value = "hello")
    public String hello(@RequestHeader(value = "a", required = false) String a,
                        @RequestHeader(value = DiscoveryConstants.HEADER_GROUP, required = false) String group,
                        @RequestHeader(value = DiscoveryConstants.HEADER_STRATEGYID, required = false) String strategyId){
        log.info("routeRequest: {}", DiscoveryContext.getRouteRequest());
        return "a=" + a + ", group = " + group + ", strategyId = " + strategyId;
    }

}
