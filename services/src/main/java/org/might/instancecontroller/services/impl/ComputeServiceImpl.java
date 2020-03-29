package org.might.instancecontroller.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.might.instancecontroller.services.BaseActions;
import org.might.instancecontroller.services.transport.RESTService;
import org.might.instancecontroller.services.transport.impl.RestResponse;
import org.might.instancecontroller.models.servers.Instance;
import org.might.instancecontroller.models.servers.Server;
import org.might.instancecontroller.models.servers.Servers;
import org.might.instancecontroller.services.ComputeService;
import org.might.instancecontroller.services.transport.RestUtils;
import org.might.instancecontroller.utils.OSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ComputeServiceImpl implements ComputeService {
    /**
     * Logger initialization.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            ComputeServiceImpl.class
    );
    /**
     * Template for message with server object.
     */
    private static final String SERVER_TEMPLATE = "Server {}";
    /**
     * Template for message with server list.
     */
    private static final String SERVERS_TEMPLATE = "Server list {}";

    private static final String SERVER_ACTION_TEMPLATE =
            "Server {} perform action {}";

    /**
     * OS utils bean.
     */
    private OSUtils osUtils;
    /**
     * Rest service bean.
     */
    private RESTService restService;

    /** Default constructor.
     * @param restService
     * @param osUtils
     */
    @Autowired
    public ComputeServiceImpl(final RESTService restService,
                              final OSUtils osUtils) {
        this.restService = restService;
        this.osUtils = osUtils;
    }

    /**
     * Return the server list.
     * @return string entity of servers.
     */
    public String getListServer() {
        RestResponse restResponse = restService.getRaw(
                osUtils.getOsComputeUrl(),
                RestUtils.getAuthHeaders(),
                new TypeReference<>(){
                });
        LOGGER.debug(SERVERS_TEMPLATE, restResponse.getStringEntity());
        return restResponse.getStringEntity();
    }

    /**
     * Return the server list.
     * @return the List<Server>.
     */
    public List<Server> getServerList() {
        Servers servers =
                restService.get(
                        osUtils.getOsComputeUrl(),
                        RestUtils.getAuthHeaders(),
                        new TypeReference<Servers>() {
                        });

        LOGGER.debug(SERVERS_TEMPLATE, servers.getServers());
        return servers.getServers();
    }

    /**
     * Return the server by Id.
     * @param serverId
     * @return the Server object.
     */
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

    /**
     * Return the server status by Id.
     * @param serverId
     * @return Status of the server.
     */
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

    /**
     * Return the server name by.
     * @param serverId
     * @return server name.
     */
    public String getServerName(final String serverId) {
        Instance instance =
                restService.get(
                        osUtils.getServerUrl(serverId),
                        RestUtils.getAuthHeaders(),
                        new TypeReference<Instance>() {
                        });
        LOGGER.debug(SERVER_TEMPLATE, instance);
        return instance.getServer().getName();
    }

    @Override
    public void stopServer(String serverId) {
        LOGGER.debug(SERVER_ACTION_TEMPLATE, serverId, BaseActions.Stop.class);
        restService.postRaw(osUtils.getServerUrlAction(serverId),
                new BaseActions.Stop(),
                RestUtils.getAuthHeaders(),
                new TypeReference<>() {
                });
    }

    @Override
    public void startServer(String serverId) {
        LOGGER.debug(SERVER_ACTION_TEMPLATE, serverId, BaseActions.Start.class);
        restService.postRaw(osUtils.getServerUrlAction(serverId),
                new BaseActions.Start(),
                RestUtils.getAuthHeaders(),
                new TypeReference<>() {
                });
    }

    @Override
    public void hardReboot(String serverId) {
        LOGGER.debug(
                SERVER_ACTION_TEMPLATE,
                serverId,
                BaseActions.HardReboot.class
        );
        restService.postRaw(osUtils.getServerUrlAction(serverId),
                new BaseActions.HardReboot(),
                RestUtils.getAuthHeaders(),
                new TypeReference<>() {
                });
    }

    @Override
    public void softReboot(String serverId) {
        LOGGER.debug(
                SERVER_ACTION_TEMPLATE,
                serverId,
                BaseActions.SoftReboot.class
        );
        restService.postRaw(osUtils.getServerUrlAction(serverId),
                new BaseActions.SoftReboot(),
                RestUtils.getAuthHeaders(),
                new TypeReference<>() {
                });
    }

    @Override
    public void deleteServer(String serverId) {
        LOGGER.debug(
                SERVER_ACTION_TEMPLATE,
                serverId,
                BaseActions.DeleteServer.class
        );
        restService.deleteRaw(osUtils.getServerUrl(serverId),
                new BaseActions.DeleteServer(),
                RestUtils.getAuthHeaders(),
                new TypeReference<>() {
                });
    }
}
