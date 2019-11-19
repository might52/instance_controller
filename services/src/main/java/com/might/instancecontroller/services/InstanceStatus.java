package com.might.instancecontroller.services;

import io.micrometer.core.instrument.util.StringUtils;

import java.util.Arrays;

public enum InstanceStatus {
    ACTIVE("ACTIVE"),
    RUN("RUN"),
    UNKNOWN("UNKNOWN");

    private String status;

    InstanceStatus(String status) {
        this.status = status;
    }

    public static InstanceStatus getInstanceStatus(String instanceStatus) {
        if (!StringUtils.isNotEmpty(instanceStatus)) {
            return UNKNOWN;
        }

        return Arrays.stream(InstanceStatus.values())
                .filter(el -> el.getValue().equalsIgnoreCase(instanceStatus))
                .findFirst().orElse(UNKNOWN);
    }

    public String getValue() {
        return this.status;
    }

}
