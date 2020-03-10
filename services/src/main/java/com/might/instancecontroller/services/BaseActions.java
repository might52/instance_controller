package com.might.instancecontroller.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;

public class BaseActions implements Serializable {

    @JsonRootName(value = "os-start")
    public static class Start implements Serializable {
    }

    @JsonRootName(value = "os-stop")
    public static class Stop implements Serializable {
    }

    @JsonRootName(value = "reboot")
    public static class SoftReboot implements Serializable {
        @JsonProperty(value = "type")
        private final String type = "SOFT";

        public String getType() {
            return type;
        }
    }

    @JsonRootName(value = "reboot")
    public static class HardReboot implements Serializable {
        @JsonProperty(value = "type")
        private final String type = "HARD";

        public String getType() {
            return type;
        }
    }
}
