package org.might.instancecontroller.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.might.instancecontroller.models.monitoring.HostCreateModel;
import org.might.instancecontroller.models.servers.OpenstackServer;
import org.might.instancecontroller.services.MonitoringService;
import org.might.instancecontroller.services.transport.RESTService;
import org.might.instancecontroller.services.transport.RestUtils;
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
    public boolean setUpMonitoring(final OpenstackServer openstackServer) {
        LOGGER.debug("Start set up VM to Monitoring: {}", openstackServer.getId());
        HostCreateModel hostCreateModel = MonitoringHelper.getHostCreateModel(openstackServer);
        LOGGER.debug("hostCreateModel: {}", hostCreateModel);
        Object object = restService.postRaw(
                settingsHelper.getZabbixUrl(),
                hostCreateModel,
                RestUtils.getAuthHeaders(),
                new TypeReference<>() {

                });
        LOGGER.debug("performed the setUp monitoring: {}", object);
        return false;
    }

    @Override
    public boolean removeMonitoring() {
        return false;
    }
}
