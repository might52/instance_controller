package org.might.instancecontroller.services;

import org.might.instancecontroller.models.monitoring.HostResponse;
import org.might.instancecontroller.models.servers.OpenstackServer;

public interface MonitoringService {
    HostResponse setUpMonitoring(OpenstackServer openstackServer);
    HostResponse removeMonitoring();
}
