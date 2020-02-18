package com.might.instancecontroller.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.might.instancecontroller.models.servers.Instance;
import com.might.instancecontroller.models.servers.Server;
import com.might.instancecontroller.models.servers.Servers;
import com.might.instancecontroller.services.ComputeService;
import com.might.instancecontroller.services.transport.RESTService;
import com.might.instancecontroller.services.transport.RestUtils;
import com.might.instancecontroller.services.transport.impl.RestResponse;
import com.might.instancecontroller.utils.OSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ComputeServiceImpl implements ComputeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputeServiceImpl.class);
    private static final String SERVER_TEMPLATE = "Server {}";
    private static final String SERVERS_TEMPLATE = "Server list {}";

    private OSUtils osUtils;
    private RESTService restService;

    @Autowired
    public ComputeServiceImpl(RESTService restService,
                              OSUtils osUtils) {
        this.restService = restService;
        this.osUtils = osUtils;
    }

    public String getListServer() {
        RestResponse restResponse = (RestResponse) restService.get(osUtils.getOsComputeUrl(),
                RestUtils.getAuthHeaders());
        LOGGER.debug(SERVERS_TEMPLATE, restResponse.getStringEntity());
        return restResponse.getStringEntity();
    }

    public List<Server> getServerList() {
        Servers servers =
                restService.get(
                        osUtils.getOsComputeUrl(),
                        RestUtils.getAuthHeaders(),
                        new TypeReference<Servers>() {
                        });

        LOGGER.debug("Server list: {}", servers.getServers());
        return servers.getServers();
    }

    public Server getServer(final String serverId) {
        Instance instance =
                restService.get(
                        osUtils.getServerUrl(serverId),
                        RestUtils.getAuthHeaders(),
                        new TypeReference<Instance>() {
                        });
        LOGGER.debug(SERVER_TEMPLATE, instance.getServer());
        return instance.getServer();
    }


    public String getServerStatus(final String serverId) {
        Instance instance =
                restService.get(
                        osUtils.getServerUrl(serverId),
                        RestUtils.getAuthHeaders(),
                        new TypeReference<Instance>() {
                        });
        LOGGER.debug(SERVER_TEMPLATE, instance);
        return instance.getServer().getStatus();
    }

    public String getInstanceName(final String serverId) {
        Instance instance =
                restService.get(
                        osUtils.getServerUrl(serverId),
                        RestUtils.getAuthHeaders(),
                        new TypeReference<Instance>() {
                        });
        LOGGER.debug(SERVER_TEMPLATE, instance);
        return instance.getServer().getName();
    }


}
