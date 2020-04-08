package org.might.instancecontroller.services;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;

public class ServerActions implements Serializable {

    private static final long serialVersionUID = 4351538343948018262L;

    @JsonRootName(value = "os-start")
    public static class Start implements Serializable {
        private static final long serialVersionUID = -5221492495819848191L;
    }

    @JsonRootName(value = "os-stop")
    public static class Stop implements Serializable {
        private static final long serialVersionUID = 6052644162472864011L;
    }

    @JsonRootName(value = "reboot")
    public static class SoftReboot implements Serializable {

        private static final long serialVersionUID = 6052800673780424098L;

        @JsonProperty(value = "type")
        private final String type = "SOFT";

        public String getType() {
            return type;
        }
    }

    @JsonRootName(value = "reboot")
    public static class HardReboot implements Serializable {

        private static final long serialVersionUID = 5746620415628710038L;

        @JsonProperty(value = "type")
        private final String type = "HARD";

        public String getType() {
            return type;
        }
    }

    @JsonIgnoreType
    public static class DeleteServer implements Serializable {
        private static final long serialVersionUID = 5056849096547051089L;
    }
}
