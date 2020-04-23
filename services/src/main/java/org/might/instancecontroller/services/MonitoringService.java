package org.might.instancecontroller.services;

import org.might.instancecontroller.models.servers.OpenstackServer;

public interface MonitoringService {
    boolean setUpMonitoring(OpenstackServer openstackServer);
    boolean removeMonitoring();
}
