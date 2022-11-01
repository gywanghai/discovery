package com.ocean.discovery.core.route.service.impl;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.ocean.discovery.core.datasource.RouteRuleDatasource;
import com.ocean.discovery.core.route.service.RouteService;
import com.ocean.discovery.core.rule.*;
import com.ocean.discovery.core.route.RouteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.*;

/**
 * @author 王海
 */
public class DefaultRouteServiceImpl implements RouteService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultRouteServiceImpl.class);

    private SecureRandom secureRandom = new SecureRandom();

    private RouteRuleDatasource datasource;

    public DefaultRouteServiceImpl(RouteRuleDatasource datasource){
        this.datasource = datasource;
    }

    @Override
    public RouteRequest flowDye(String group, Map<String, List<String>> headers) {
        RouteCondition routeCondition = selectStrategy(headers);
        if(routeCondition == null){
            return null;
        }
        RouteWeight routeWeight = select(routeCondition);
        String strategyId = routeWeight.getStrategyId();
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setHeaders(headers);
        routeRequest.setStrategyId(strategyId);
        routeRequest.setGroup(group);
        return routeRequest;
    }

    @Override
    public RouteResult select(String group, String serviceName, String strategyId) {
        RouteRule routeRule = this.datasource.getRouteRule();
        RouteStrategyItem routeStrategyItem = routeRule.getStrategyItem(strategyId, serviceName);
        if(routeStrategyItem == null){
            return null;
        }
        return new RouteResult(group, routeStrategyItem.getServiceName(), routeStrategyItem.getVersion());
    }

    private RouteWeight select(RouteCondition routeCondition) {
        List<RouteWeight> routeWeights = routeCondition.getRouteWeights();
        int[] weights = new int[routeWeights.size() + 1];
        int sum = 0;
        for(int index = 0; index < routeWeights.size(); index++){
            sum = sum + routeWeights.get(index).getWeight();
            weights[index + 1] = sum;
        }
        int random = secureRandom.nextInt(weights[weights.length - 1]);
        int targetIndex = 0;
        for (int index = 0; index < weights.length; index++){
            if(random < weights[index]){
                targetIndex = index - 1;
                break;
            }
        }
        return routeWeights.get(targetIndex);
    }

    private RouteCondition selectStrategy(Map<String, List<String>> headers) {
        RouteRule routeRule = this.datasource.getRouteRule();
        List<RouteCondition> conditions = routeRule.getConditions();
        RouteCondition validCondition = null;
        for (RouteCondition routeCondition : conditions){
            String express = routeCondition.getExpression();
            // 如果表达式不为空
            if(express != null && express.trim().length() > 0){
                boolean pass = calcExpression(express, headers);
                if(!pass){
                    continue;
                }
                validCondition = routeCondition;
                break;
            }
            // 如果表达式为空，则使用
            else {
                validCondition = routeCondition;
            }
        }
        return validCondition;
    }

    private boolean calcExpression(String expression, Map headers) {
        Expression compiledExp = AviatorEvaluator.compile(expression);
        Boolean result = Boolean.FALSE;
        try{
            result = (Boolean) compiledExp.execute(headers);
        }catch (Exception e){
            logger.error("执行表达式[{}]失败", expression, e);
        }
        return result;
    }
}
