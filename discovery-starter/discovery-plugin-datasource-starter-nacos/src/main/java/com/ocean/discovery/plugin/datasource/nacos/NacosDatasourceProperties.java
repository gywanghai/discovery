package com.ocean.discovery.plugin.datasource.nacos;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.StringJoiner;

/**
 * @author 王海
 */
@Configuration
@ConfigurationProperties(prefix = "discovery.datasource.nacos")
public class NacosDatasourceProperties {

    private String serverAddr;

    private String dataId;

    private String namespace;

    private String group;

    private String accessKey;

    private String secretKey;

    public String getServerAddr() {
        return serverAddr;
    }

    public NacosDatasourceProperties setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
        return this;
    }

    public String getDataId() {
        return dataId;
    }

    public NacosDatasourceProperties setDataId(String dataId) {
        this.dataId = dataId;
        return this;
    }

    public String getNamespace() {
        return namespace;
    }

    public NacosDatasourceProperties setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public NacosDatasourceProperties setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public NacosDatasourceProperties setAccessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public NacosDatasourceProperties setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NacosDatasourceProperties.class.getSimpleName() + "[", "]")
                .add("dataId='" + dataId + "'")
                .add("namespace='" + namespace + "'")
                .add("group='" + group + "'")
                .toString();
    }
}
