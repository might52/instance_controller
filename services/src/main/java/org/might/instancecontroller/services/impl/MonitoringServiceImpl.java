package org.might.instancecontroller.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.might.instancecontroller.dba.entity.Server;
import org.might.instancecontroller.models.monitoring.HostCreateModel;
import org.might.instancecontroller.models.monitoring.HostDeletionModel;
import org.might.instancecontroller.models.monitoring.HostResponse;
import org.might.instancecontroller.models.servers.OpenstackServer;
import org.might.instancecontroller.services.MonitoringService;
import org.might.instancecontroller.services.transport.RESTService;
import org.might.instancecontroller.utils.FunctionHelper;
import org.might.instancecontroller.utils.MonitoringHelper;
import org.might.instancecontroller.utils.SettingsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    /**
     * Logger initialization.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            MonitoringServiceImpl.class
    );

    private static final String MONITORING_CREATION_TEMPLATE = "performed the setUp monitoring, hostId: {}";
    private static final String MONITORING_DELETION_TEMPLATE = "performed the remove monitoring, hostId: {}";
    private RESTService restService;
    private SettingsHelper settingsHelper;

    @Autowired
    public MonitoringServiceImpl(RESTService restService,
                                 SettingsHelper settingsHelper) {
        this.restService = restService;
        this.settingsHelper = settingsHelper;
    }

    @Override
    public HostResponse setUpMonitoring(final OpenstackServer openstackServer) {
        LOGGER.debug("Perform set up monitoring for vm {}, ip address: {}, hostname: {}",
                openstackServer.getId(),
                openstackServer.getAddresses()
                        .getNetworks()
                        .get(FunctionHelper.NETWORK_NAME_PUBLIC)
                        .get(0)
                        .getAddr(),
                openstackServer.getName()
        );
        HostCreateModel hostCreateModel = MonitoringHelper.getHostCreateModel(openstackServer);
        HostResponse hostResponse = restService.post(
                settingsHelper.getZabbixUrl(),
                hostCreateModel,
                null,
                new TypeReference<HostResponse>() {
                });
        LOGGER.debug(MONITORING_CREATION_TEMPLATE, hostResponse.getResult().getHostids().get(0));
        return hostResponse;
    }

    @Override
    public HostResponse removeMonitoring(Server server) {
        LOGGER.debug("Removing server with id: {} " +
                        "in monitoring system with HostId: {} " +
                        "and OpesntackId: {}",
                server.getId(),
                server.getMonitoringId(),
                server.getServerId()
        );
        HostDeletionModel hostDeletionModel = MonitoringHelper.getHostDeletionModel(server);
        HostResponse hostResponse = restService.post(
                settingsHelper.getZabbixUrl(),
                hostDeletionModel,
                null,
                new TypeReference<HostResponse>() {
                });
        LOGGER.debug(MONITORING_DELETION_TEMPLATE, hostResponse.getResult().getHostids().get(0));
        return hostResponse;
    }
}
