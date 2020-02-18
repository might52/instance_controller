package com.might.instancecontroller.services;

import com.might.instancecontroller.models.servers.Server;

import java.util.List;

public interface ComputeService {
    String getListServer();
    String getServerStatus(String serverId);
    String getInstanceName(String serverId);
    List<Server> getServerList();
    Server getServer(String serverId);
}
