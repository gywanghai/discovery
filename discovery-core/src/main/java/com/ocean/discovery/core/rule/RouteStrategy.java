package com.ocean.discovery.core.rule;

import java.util.Collections;
import java.util.Set;

/**
 * 路由策略
 */
public class RouteStrategy {

    /**
     * 路由策略ID
     */
    private String id;

    /**
     * 具体的路由策略
     */
    private Set<RouteStrategyItem> rules;

    public RouteStrategy(){

    }

    public RouteStrategy(String id, Set<RouteStrategyItem> rules){
        this.id = id;
        this.rules = Collections.unmodifiableSet(rules);
    }

    public String getId() {
        return id;
    }

    public RouteStrategy setId(String id) {
        this.id = id;
        return this;
    }

    public Set<RouteStrategyItem> getRules() {
        return rules;
    }

    public RouteStrategy setRules(Set<RouteStrategyItem> rules) {
        this.rules = rules;
        return this;
    }
}
