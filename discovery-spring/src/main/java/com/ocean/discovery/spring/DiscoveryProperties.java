package com.ocean.discovery.spring;

import com.ocean.discovery.core.vo.Metadata;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.StringJoiner;

@Configuration
@ConfigurationProperties(prefix = "discovery")
public class DiscoveryProperties {

    private Metadata metadata;

    public Metadata getMetadata() {
        return metadata;
    }

    public DiscoveryProperties setMetadata(Metadata metadata) {
        this.metadata = metadata;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DiscoveryProperties.class.getSimpleName() + "[", "]")
                .add("metadata=" + metadata)
                .toString();
    }
}
