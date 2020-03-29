package org.might.instancecontroller.services;

import org.might.instancecontroller.models.servers.Server;

import java.util.List;

public interface ComputeService {
    String getListServer();
    String getServerStatus(String serverId);
    String getServerName(String serverId);
    List<Server> getServerList();
    Server getServer(String serverId);
    void stopServer(String serverId);
    void startServer(String serverId);
    void hardReboot(String serverId);
    void softReboot(String serverId);
    void deleteServer(String serverId);
}
