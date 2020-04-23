package org.might.instancecontroller.services;

import org.might.instancecontroller.models.servers.OpenstackServer;

public interface MonitoringService {
    void setUpMonitoring(OpenstackServer openstackServer);
    void removeMonitoring();
}
