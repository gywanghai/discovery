package com.ocean.discovery.plugin.datasource.nacos;

import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.config.NacosConfigService;
import com.alibaba.nacos.common.executor.NameThreadFactory;
import com.google.common.base.Preconditions;
import com.ocean.discovery.core.datasource.RouteRuleDatasource;
import com.ocean.discovery.core.rule.RouteRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NacosDatasource implements RouteRuleDatasource, EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(NacosDatasource.class);
    @Autowired
    private NacosDatasourceProperties nacosDatasourceProperties;

    private Environment environment;

    private NacosConfigService configService;

    private String dataId;

    private RouteRule routeRule;

    private Executor executor = new ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10), new NameThreadFactory("Discovery-Nacos-Datasource-"),
            new ThreadPoolExecutor.AbortPolicy());

    @PostConstruct
    public void init() throws NacosException {
        Properties properties = new Properties();
        properties.put("serverAddr", nacosDatasourceProperties.getServerAddr());
        if(StringUtils.hasLength(nacosDatasourceProperties.getNamespace())){
            properties.put("namespace", nacosDatasourceProperties.getNamespace());
        }
        if(StringUtils.hasLength(nacosDatasourceProperties.getAccessKey())){
            properties.put("accessKey", nacosDatasourceProperties.getAccessKey());
        }
        if(StringUtils.hasLength(nacosDatasourceProperties.getSecretKey())){
            properties.put("secretKey", nacosDatasourceProperties.getSecretKey());
        }
        Preconditions.checkArgument(StringUtils.hasLength(dataId), "dataId must not be empty");
        this.configService = (NacosConfigService) NacosFactory.createConfigService(properties);
        String config = this.configService.getConfigAndSignListener(dataId, nacosDatasourceProperties.getGroup(), 10, new Listener() {
            @Override
            public Executor getExecutor() {
                return executor;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                logger.info("接收到新的消息: {}", configInfo);
                NacosDatasource.this.routeRule =  JSONUtil.toBean(configInfo, RouteRule.class);
            }
        });
        this.routeRule =  JSONUtil.toBean(config, RouteRule.class);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        String env = environment.getActiveProfiles().length == 0? "" : environment.getActiveProfiles()[0];
        if (StringUtils.isEmpty(env)) {
            this.dataId = nacosDatasourceProperties.getDataId() + ".json";
        }
        else {
            this.dataId = nacosDatasourceProperties.getDataId() + "-" + env + ".json";
        }
    }

    @Override
    public RouteRule getRouteRule() {
        if(this.routeRule != null){
            return this.routeRule;
        }
        try {
            this.init();
        } catch (NacosException e) {
            logger.error("Load Discovery Config error!", e);
        }
        return this.routeRule;
    }
}
