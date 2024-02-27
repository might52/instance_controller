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

import org.might.instancecontroller.models.function.ServerCreateModel;
import org.might.instancecontroller.models.servers.OpenstackServer;

import java.util.List;

public interface ComputeService {
    /**
     * Return server list.
     * @return server list {@link String}
     */
    String getListServer();

    /**
     * Get server status by id.
     * @param serverId server id.
     * @return representation state of server.
     */
    String getServerStatus(String serverId);

    /**
     * Get server name by id.
     * @param serverId server id.
     * @return representation name of server
     */
    String getServerName(String serverId);

    /**
     * Get {@link OpenstackServer} list.
     * @return {@link List<OpenstackServer>}
     */
    List<OpenstackServer> getServerList();

    /**
     * Get {@link OpenstackServer} by id.
     * @param serverId server id.
     * @return {@link OpenstackServer}
     */
    OpenstackServer getServer(String serverId);

    /**
     * Perform Stop server action by server id.
     * @param serverId server id.
     */
    void stopServer(String serverId);

    /**
     * Perform Start server action by server id.
     * @param serverId server id.
     */
    void startServer(String serverId);

    /**
     * Perform Hard Reboot server action by server id.
     * @param serverId server id.
     */
    void hardReboot(String serverId);

    /**
     * Perform Soft Reboot server action by server id.
     * @param serverId server id.
     */
    void softReboot(String serverId);

    /**
     * Perform Delete server action by server id.
     * @param serverId server id.
     */
    void deleteServer(String serverId);

    /**
     * Perform Pause server action by server id.
     * @param serverId
     */
    void pauseServer(String serverId);

    /**
     * Perform UnPause server action by server id.
     * @param serverId
     */
    void unPauseServer(String serverId);

    /**
     * Perform Create server action by server id.
     * @param serverCreateModel {@link ServerCreateModel}.
     * @return created {@link OpenstackServer}
     */
    OpenstackServer createServer(ServerCreateModel serverCreateModel);
}
