package com.ocean.discovery.plugin.gateway;

import com.ocean.discovery.core.datasource.RouteRuleDatasource;
import com.ocean.discovery.core.route.service.RouteService;
import com.ocean.discovery.core.route.service.impl.DefaultRouteServiceImpl;
import com.ocean.discovery.core.rule.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 王海
 */
@Configuration
public class DiscoveryGatewayConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RouteService routeService(RouteRuleDatasource datasource){
        return new DefaultRouteServiceImpl(datasource);
    }

    @Bean
    @ConditionalOnMissingBean
    public DiscoveryFlowDyeGlobalFilter discoveryFlowDyeGlobalFilter(){
        return new DiscoveryFlowDyeGlobalFilter();
    }

}
