package org.might.instancecontroller.services;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum FunctionStatus {
    /**
     * The function has active state.
     */
    ACTIVE("ACTIVE"),
    /**
     * The function has not finished the original build process.
     */
    IN_PROGRESS("IN PROGRESS OF BUILD"),
    /**
     * The function has problem.
     */
    HAS_PROBLEM("HAS_PROBLEM"),
    /**
     * Function has a critical status.
      */
    CRITICAL("CRITICAL"),
    /**
     * The function state is unknown.
     */
    UNKNOWN("UNKNOWN");

    private String status;

    FunctionStatus(String status) {
        this.status = status;
    }

    public static FunctionStatus getFunctionStatus(String functionStatus) {
        if (!StringUtils.isNotEmpty(functionStatus)) {
            return UNKNOWN;
        }

        return Arrays.stream(FunctionStatus.values())
                .filter(el -> el.getValue().equalsIgnoreCase(functionStatus))
                .findFirst().orElse(UNKNOWN);
    }

    public String getValue() {
        return this.status;
    }

}
