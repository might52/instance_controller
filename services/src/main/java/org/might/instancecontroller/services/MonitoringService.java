package org.might.instancecontroller.services;

import org.might.instancecontroller.dba.entity.Server;
import org.might.instancecontroller.models.monitoring.HostResponse;
import org.might.instancecontroller.models.servers.OpenstackServer;

public interface MonitoringService {
    /**
     * SetUp server instance to monitoring by {@link OpenstackServer}.
     * @param openstackServer
     * @return {@link HostResponse}
     */
    HostResponse setUpMonitoring(OpenstackServer openstackServer);
    HostResponse removeMonitoring(Server server);
}
