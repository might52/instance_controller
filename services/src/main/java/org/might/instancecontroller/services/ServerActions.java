/*
 * MIT License
 *
 * Copyright (c) 2024 Andrei F._
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.might.instancecontroller.services;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;

public class ServerActions implements Serializable {

    private static final long serialVersionUID = 4351538343948018262L;

    /**
     * Get Start server action.
     */
    @JsonRootName(value = "os-start")
    public static class Start implements Serializable {
        private static final long serialVersionUID = -5221492495819848191L;
    }

    /**
     * Get Stop server action.
     */
    @JsonRootName(value = "os-stop")
    public static class Stop implements Serializable {
        private static final long serialVersionUID = 6052644162472864011L;
    }

    /**
     * Get Soft Reboot server action.
     */
    @JsonRootName(value = "reboot")
    public static class SoftReboot implements Serializable {

        private static final long serialVersionUID = 6052800673780424098L;

        @JsonProperty(value = "type")
        private final String type = "SOFT";

        public String getType() {
            return type;
        }
    }

    /**
     * Get Hard Reboot server action.
     */
    @JsonRootName(value = "reboot")
    public static class HardReboot implements Serializable {

        private static final long serialVersionUID = 5746620415628710038L;

        @JsonProperty(value = "type")
        private final String type = "HARD";

        public String getType() {
            return type;
        }
    }

    /**
     * Get Pause server action.
     */
    @JsonRootName(value = "pause")
    public static class Pause implements Serializable {
        private static final long serialVersionUID = -1258937008538157626L;
    }

    /**
     * Get Unpause server action.
     */
    @JsonRootName(value = "unpause")
    public static class UnPause implements Serializable {
        private static final long serialVersionUID = -3084792697676427702L;
    }

    /**
     * Get Delete server action.
     */
    @JsonIgnoreType
    public static class DeleteServer implements Serializable {
        private static final long serialVersionUID = 5056849096547051089L;
    }
}
