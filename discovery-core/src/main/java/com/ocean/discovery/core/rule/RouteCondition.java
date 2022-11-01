package com.ocean.discovery.core.rule;

import java.util.*;

/**
 * 路由条件，
 */
public class RouteCondition {
    /**
     * 路由条件ID
     */
    private String id;
    /**
     * 条件表达式
     */
    private String expression;
    /**
     * 路由策略权重信息
     */
    private List<RouteWeight> routeWeights;

    public RouteCondition(){

    }

    public RouteCondition(String id, String expression, List<RouteWeight> routeWeights){
        this.id = id;
        this.expression = expression;
        this.routeWeights = Collections.unmodifiableList(routeWeights);
    }

    public String getId() {
        return id;
    }

    public RouteCondition setId(String id) {
        this.id = id;
        return this;
    }

    public String getExpression() {
        return expression;
    }

    public RouteCondition setExpression(String expression) {
        this.expression = expression;
        return this;
    }

    public List<RouteWeight> getRouteWeights() {
        return routeWeights;
    }

    public RouteCondition setRouteWeights(List<RouteWeight> routeWeights) {
        this.routeWeights = routeWeights;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RouteCondition.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("expression='" + expression + "'")
                .add("routeWeights=" + routeWeights)
                .toString();
    }
}
