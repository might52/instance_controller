package com.might.instancecontroller.services;

import com.might.instancecontroller.models.servers.Server;

import java.util.List;

public interface ComputeService {
    String getListServer();
    String getServerStatus(String serverId);
    String getServerName(String serverId);
    List<Server> getServerList();
    Server getServer(String serverId);
    void stopServer(String serverId);
    void startServer(String serverId);
}
