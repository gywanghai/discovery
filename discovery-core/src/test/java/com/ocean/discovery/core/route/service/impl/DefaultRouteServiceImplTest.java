package com.ocean.discovery.core.route.service.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.ocean.discovery.core.datasource.RouteRuleDatasource;
import com.ocean.discovery.core.route.RouteRequest;
import com.ocean.discovery.core.route.service.RouteService;
import com.ocean.discovery.core.rule.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class DefaultRouteServiceImplTest {

    private RouteService routeService;

    @Before
    public void before(){
        RouteRule routeRule = new RouteRule();
        List<RouteCondition> conditions = new ArrayList<>();
        routeRule.setConditions(conditions);


        Set<RouteStrategy> strategies = new HashSet<>();

        Set<RouteStrategyItem> grayRouteStrategyItems = new HashSet<>();
        grayRouteStrategyItems.add(new RouteStrategyItem("tulingmall-member", "1.0"));
        grayRouteStrategyItems.add(new RouteStrategyItem("tulingmall-promotion","1.0"));
        strategies.add(new RouteStrategy("grayRoute", grayRouteStrategyItems));

        Set<RouteStrategyItem> stableRouteStrategyItems = new HashSet<>();
        stableRouteStrategyItems.add(new RouteStrategyItem("tulingmall-member", "1.1"));
        stableRouteStrategyItems.add(new RouteStrategyItem("tulingmall-promotion","1.1"));
        strategies.add(new RouteStrategy("stableRoute", stableRouteStrategyItems));

        routeRule.setStrategies(strategies);

        List<RouteCondition> conditionList = new ArrayList<>();

        List<RouteWeight> routeWeights1 = new ArrayList<>();
        routeWeights1.add(new RouteWeight("grayRoute", 90));
        routeWeights1.add(new RouteWeight("stableRoute", 10));

        List<RouteWeight> routeWeights2 = new ArrayList<>();
        routeWeights2.add(new RouteWeight("grayRoute", 0));
        routeWeights2.add(new RouteWeight("stableRoute", 100));


        conditionList.add(new RouteCondition("condition1","a[0] == '1'", routeWeights1));
        conditionList.add(new RouteCondition("condition2", "", routeWeights2));
        routeRule.setConditions(conditionList);

        System.out.println(JSONUtil.toJsonPrettyStr(routeRule));

        RouteRuleDatasource datasource = new RouteRuleDatasource() {
            @Override
            public RouteRule getRouteRule() {
                return routeRule;
            }
        };

        routeService = new DefaultRouteServiceImpl(datasource);
    }

    @Test
    public void testSelect(){
        Map<String, List<String>> headers = new HashMap<>();
        List<String> valueList = new ArrayList<>();
        valueList.add("1");
        headers.put("a", valueList);
        RouteRequest routeRequest = routeService.flowDye("group", headers);
        System.out.println(routeRequest);
    }
}
