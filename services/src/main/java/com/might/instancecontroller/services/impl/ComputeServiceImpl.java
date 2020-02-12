package com.might.instancecontroller.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.might.instancecontroller.annotations.RequireConnection;
import com.might.instancecontroller.models.servers.Instance;
import com.might.instancecontroller.models.servers.Server;
import com.might.instancecontroller.services.ComputeService;
import com.might.instancecontroller.services.transport.RESTService;
import com.might.instancecontroller.services.transport.RestUtils;
import com.might.instancecontroller.services.transport.impl.RestResponse;
import com.might.instancecontroller.utils.AuthSessionBean;
import com.might.instancecontroller.utils.OSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ComputeServiceImpl implements ComputeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputeServiceImpl.class);
    private static final String INSTANCE_TEMPLATE = "Instance {}";

    private OSUtils osUtils;
    private RESTService restService;
    private AuthSessionBean authSessionBean;

    @Autowired
    public ComputeServiceImpl(RESTService restService,
                              OSUtils osUtils,
                              AuthSessionBean authSessionBean) {
        this.restService = restService;
        this.osUtils = osUtils;
        this.authSessionBean = authSessionBean;
    }

    @Override
    @RequireConnection
    public Object getListInstance() {
        RestResponse restResponse = (RestResponse) restService.get(osUtils.getOsComputeUrl(),
                RestUtils.getAuthHeaders());
        LOGGER.debug("Instance list: {}", restResponse.getStringEntity());
        return restResponse;
    }

    @RequireConnection
    public String getInstanceStatus(final String instanceId) {
        Instance instance =
                restService.get(
                        osUtils.getServerUrl(instanceId),
                        RestUtils.getAuthHeaders(),
                        new TypeReference<Instance>() {});
        LOGGER.debug(INSTANCE_TEMPLATE, instance);
        return instance.getServer().getStatus();
    }

    @RequireConnection
    public String getInstanceName(final String instanceId) {
        Instance instance =
                restService.get(
                    osUtils.getServerUrl(instanceId),
                    RestUtils.getAuthHeaders(),
                    new TypeReference<Instance>() {});
        LOGGER.debug(INSTANCE_TEMPLATE, instance);
        return instance.getServer().getName();
    }

    @RequireConnection
    public List<Instance> getInstanceList(){
        List<Instance> instances =
                restService.get(
                        osUtils.getOsComputeUrl(),
                        RestUtils.getAuthHeaders(),
                        new TypeReference<List<Instance>>() {
                        });


        return instances;
    }

}
