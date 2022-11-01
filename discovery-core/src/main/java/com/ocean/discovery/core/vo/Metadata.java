package com.ocean.discovery.core.vo;

import java.util.StringJoiner;

public class Metadata {

    private String group;

    private String version;

    public String getGroup() {
        return group;
    }

    public Metadata setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public Metadata setVersion(String version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Metadata.class.getSimpleName() + "[", "]")
                .add("group='" + group + "'")
                .add("version='" + version + "'")
                .toString();
    }
}
