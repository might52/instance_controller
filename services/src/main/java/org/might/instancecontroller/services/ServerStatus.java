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


import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum ServerStatus {
    /**
     * The server is active.
     */
    ACTIVE("ACTIVE"),
    /**
     * The server has not finished the original build process.
     */
    BUILD("BUILD"),
    /**
     * The server is permanently deleted.
     */
    DELETED("DELETED"),
    /**
     * The server is in error.
     */
    ERROR("ERROR"),
    /**
     * The server is hard rebooting. This is equivalent to pulling
     * the power plug on a physical server, plugging it back in,
     * and rebooting it.
     */
    HARD_REBOOT("HARD_REBOOT"),
    /**
     * The server is being migrated to a new host.
     */
    MIGRATING("MIGRATING"),
    /**
     * The password is being reset on the server.
     */
    PASSWORD("PASSWORD"),
    /**
     * In a paused state, the state of the server is
     * stored in RAM. A paused server continues to run in frozen state.
     */
    PAUSED("PAUSED"),
    /**
     * The server is in a soft reboot state. A reboot command was passed to the operating system.
     */
    REBOOT("REBOOT"),
    /**
     * The server is currently being rebuilt from an image.
     */
    REBUILD("REBUILD"),
    /**
     * The server is in rescue mode. A rescue image is
     * running with the original server image attached.
     */
    RESCUE("RESCUE"),
    /**
     * Instance is performing the differential copy of data
     * that changed during its initial copy. Instance is down for this stage.
     */
    RESIZE("RESIZE"),
    /**
     * The resize or migration of a server failed for some reason.
     * The destination server is being cleaned up and the
     * original source server is restarting.
     */
    REVERT_RESIZE("REVERT_RESIZE"),
    /**
     * The server is in shelved state. Depending on the
     * shelve offload time, the server will be automatically shelved offloaded.
     */
    SHELVED("SHELVED"),
    /**
     * The shelved server is offloaded (removed from the compute host)
     * and it needs unshelved action to be used again.
     */
    SHELVED_OFFLOADED("SHELVED_OFFLOADED"),
    /**
     * The server is powered off and the disk image still persists.
     */
    SHUTOFF("SHUTOFF"),
    /**
     * The server is marked as deleted but the disk
     * images are still available to restore.
     */
    SOFT_DELETED("SOFT_DELETED"),
    /**
     * The server is suspended, either by request or necessity.
     * When you suspend a server, its state is stored on disk,
     * all memory is written to disk, and the server is stopped.
     * Suspending a server is similar to placing a device in hibernation
     * and its occupied resource will not be freed but rather kept for
     * when the server is resumed. If a server is infrequently used
     * and the occupied resource needs to be freed to create other servers,
     * it should be shelved.
     */
    SUSPENDED("SUSPENDED"),
    /**
     * The state of the server is unknown. Contact your cloud provider.
     */
    UNKNOWN("UNKNOWN"),
    /**
     * . System is awaiting confirmation that the server is operational after a move or resize.
     */
    VERIFY_RESIZE("VERIFY_RESIZE");

    private final String status;

    ServerStatus(String status) {
        this.status = status;
    }

    public static ServerStatus getServerStatus(String instanceStatus) {
        if (!StringUtils.isNotEmpty(instanceStatus)) {
            return UNKNOWN;
        }

        return Arrays.stream(ServerStatus.values())
                .filter(el -> el.getValue().equalsIgnoreCase(instanceStatus))
                .findFirst().orElse(UNKNOWN);
    }

    public String getValue() {
        return this.status;
    }

}
