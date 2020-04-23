package org.might.instancecontroller.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.might.instancecontroller.models.monitoring.HostCreateModel;
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

    private RESTService restService;
    private SettingsHelper settingsHelper;

    @Autowired
    public MonitoringServiceImpl(RESTService restService,
                                 SettingsHelper settingsHelper) {
        this.restService = restService;
        this.settingsHelper = settingsHelper;
    }

    @Override
    public void setUpMonitoring(final OpenstackServer openstackServer) {
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
        Object object = restService.postRaw(
                settingsHelper.getZabbixUrl(),
                hostCreateModel,
                null,
                new TypeReference<>() {
                });
        LOGGER.debug("performed the setUp monitoring: {}", object);
    }

    @Override
    public void removeMonitoring() {
    }
}
