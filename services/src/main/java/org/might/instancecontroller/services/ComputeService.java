package org.might.instancecontroller.services;

import org.might.instancecontroller.models.function.ServerCreateModel;
import org.might.instancecontroller.models.servers.OpenstackServer;

import java.util.List;

public interface ComputeService {
    String getListServer();
    String getServerStatus(String serverId);
    String getServerName(String serverId);
    List<OpenstackServer> getServerList();
    OpenstackServer getServer(String serverId);
    void stopServer(String serverId);
    void startServer(String serverId);
    void hardReboot(String serverId);
    void softReboot(String serverId);
    void deleteServer(String serverId);
    OpenstackServer createServer(ServerCreateModel function);
}
