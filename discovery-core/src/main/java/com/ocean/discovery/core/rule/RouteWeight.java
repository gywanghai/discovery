package com.ocean.discovery.core.rule;

import java.util.StringJoiner;

/**
 * 路由策略权重配置信息
 */
public class RouteWeight {
    /**
     * 路由策略ID
     */
    private String strategyId;
    /**
     * 权重，1-100
     */
    private Integer weight;

    public RouteWeight(){

    }

    public RouteWeight(String strategyId, Integer weight){
        this.strategyId = strategyId;
        this.weight = weight;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public RouteWeight setStrategyId(String strategyId) {
        this.strategyId = strategyId;
        return this;
    }

    public Integer getWeight() {
        return weight;
    }

    public RouteWeight setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RouteWeight.class.getSimpleName() + "[", "]")
                .add("strategyId='" + strategyId + "'")
                .add("weight=" + weight)
                .toString();
    }
}
