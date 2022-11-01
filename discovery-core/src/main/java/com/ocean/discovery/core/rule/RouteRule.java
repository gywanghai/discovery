package com.ocean.discovery.core.rule;

import java.util.*;

/**
 * 路由规则
 * @author 王海
 */
public class RouteRule {
    /**
     * 标签分组
     */
    private String group;

    /**
     * 路由条件集合
     */
    private List<RouteCondition> conditions = Collections.emptyList();

    /**
     * 路由策略集合
     */
    private Set<RouteStrategy> strategies = Collections.emptySet();

    public List<RouteCondition> getConditions() {
        return conditions;
    }

    public RouteRule setConditions(List<RouteCondition> conditions) {
        this.conditions = conditions;
        return this;
    }

    public Set<RouteStrategy> getStrategies() {
        return strategies;
    }

    public RouteRule setStrategies(Set<RouteStrategy> strategies) {
        this.strategies = strategies;
        return this;
    }

    public RouteStrategyItem getStrategyItem(String strategyId, String serviceName) {
        Optional<RouteStrategy> routeStrategyOptional = strategies.stream().filter(strategy -> strategyId.equals(strategy.getId())).findAny();
        if(routeStrategyOptional.isPresent()){
            Optional<RouteStrategyItem> routeStrategyItemOptional = routeStrategyOptional.get().getRules().stream().
                    filter(item -> item.getServiceName().equals(serviceName)).findAny();
            if(routeStrategyItemOptional.isPresent()){
                return routeStrategyItemOptional.get();
            }
        }
        return null;
    }

    public String getGroup() {
        return group;
    }

    public RouteRule setGroup(String group) {
        this.group = group;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RouteRule.class.getSimpleName() + "[", "]")
                .add("conditions=" + conditions)
                .add("strategies=" + strategies)
                .toString();
    }

}
