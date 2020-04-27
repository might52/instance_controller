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
     * Perform Create server action by server id.
     * @param serverCreateModel {@link ServerCreateModel}.
     * @return created {@link OpenstackServer}
     */
    OpenstackServer createServer(ServerCreateModel serverCreateModel);
}
