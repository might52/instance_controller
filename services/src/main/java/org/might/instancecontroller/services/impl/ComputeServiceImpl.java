package org.might.instancecontroller.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.might.instancecontroller.models.function.ServerCreateModel;
import org.might.instancecontroller.services.ServerActions;
import org.might.instancecontroller.services.transport.RESTService;
import org.might.instancecontroller.services.transport.impl.RestResponse;
import org.might.instancecontroller.models.servers.Instance;
import org.might.instancecontroller.models.servers.OpenstackServer;
import org.might.instancecontroller.models.servers.Servers;
import org.might.instancecontroller.services.ComputeService;
import org.might.instancecontroller.services.transport.RestUtils;
import org.might.instancecontroller.utils.SettingsHelper;
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
    private static final String SERVER_TEMPLATE = "OpenstackServer {}";
    /**
     * Template for message with server list.
     */
    private static final String SERVERS_TEMPLATE = "OpenstackServer list {}";

    private static final String SERVER_ACTION_TEMPLATE =
            "OpenstackServer {} perform action {}";

    private static final String SERVER_INSTANTIATE =
                    "Name: {}, " +
                    "Zone: {}, " +
                    "ImageRef: {}, " +
                    "FlavorRef: {}, " +
                    "DiskConfig: {}, " +
                    "Networks: {}";
    /**
     * OS utils bean.
     */
    private SettingsHelper settingsHelper;
    /**
     * Rest service bean.
     */
    private RESTService restService;

    /** Default constructor.
     * @param restService
     * @param settingsHelper
     */
    @Autowired
    public ComputeServiceImpl(final RESTService restService,
                              final SettingsHelper settingsHelper) {
        this.restService = restService;
        this.settingsHelper = settingsHelper;
    }

    /**
     * Return the server list.
     * @return string entity of servers.
     */
    public String getListServer() {
        RestResponse restResponse = restService.getRaw(
                settingsHelper.getServerDetailsUrl(),
                RestUtils.getAuthHeaders(),
                new TypeReference<>(){
                });
        LOGGER.debug(SERVERS_TEMPLATE, restResponse.getStringEntity());
        return restResponse.getStringEntity();
    }

    /**
     * Return the server list.
     * @return the List<OpenstackServer>.
     */
    public List<OpenstackServer> getServerList() {
        Servers servers =
                restService.get(
                        settingsHelper.getServerDetailsUrl(),
                        RestUtils.getAuthHeaders(),
                        new TypeReference<Servers>() {
                        });

        LOGGER.debug(SERVERS_TEMPLATE, servers.getOpenstackServers());
        return servers.getOpenstackServers();
    }

    /**
     * Return the server by Id.
     * @param serverId
     * @return the OpenstackServer object.
     */
    public OpenstackServer getServer(final String serverId) {
        Instance instance =
                restService.get(
                        settingsHelper.getServerUrl(serverId),
                        RestUtils.getAuthHeaders(),
                        new TypeReference<Instance>() {
                        });
        LOGGER.debug(SERVER_TEMPLATE, instance.getOpenstackServer());
        return instance.getOpenstackServer();
    }

    /**
     * Return the server status by Id.
     * @param serverId
     * @return Status of the server.
     */
    public String getServerStatus(final String serverId) {
        Instance instance =
                restService.get(
                        settingsHelper.getServerUrl(serverId),
                        RestUtils.getAuthHeaders(),
                        new TypeReference<Instance>() {
                        });
        LOGGER.debug(SERVER_TEMPLATE, instance);
        return instance.getOpenstackServer().getStatus();
    }

    /**
     * Return the server name by.
     * @param serverId
     * @return server name.
     */
    public String getServerName(final String serverId) {
        Instance instance =
                restService.get(
                        settingsHelper.getServerUrl(serverId),
                        RestUtils.getAuthHeaders(),
                        new TypeReference<Instance>() {
                        });
        LOGGER.debug(SERVER_TEMPLATE, instance);
        return instance.getOpenstackServer().getName();
    }

    @Override
    public void stopServer(final String serverId) {
        LOGGER.debug(SERVER_ACTION_TEMPLATE, serverId, ServerActions.Stop.class);
        restService.postRaw(settingsHelper.getServerUrlAction(serverId),
                new ServerActions.Stop(),
                RestUtils.getAuthHeaders(),
                new TypeReference<>() {
                });
    }

    @Override
    public void startServer(final String serverId) {
        LOGGER.debug(SERVER_ACTION_TEMPLATE, serverId, ServerActions.Start.class);
        restService.postRaw(settingsHelper.getServerUrlAction(serverId),
                new ServerActions.Start(),
                RestUtils.getAuthHeaders(),
                new TypeReference<>() {
                });
    }

    @Override
    public void hardReboot(final String serverId) {
        LOGGER.debug(
                SERVER_ACTION_TEMPLATE,
                serverId,
                ServerActions.HardReboot.class
        );
        restService.postRaw(settingsHelper.getServerUrlAction(serverId),
                new ServerActions.HardReboot(),
                RestUtils.getAuthHeaders(),
                new TypeReference<>() {
                });
    }

    @Override
    public void softReboot(final String serverId) {
        LOGGER.debug(
                SERVER_ACTION_TEMPLATE,
                serverId,
                ServerActions.SoftReboot.class
        );
        restService.postRaw(settingsHelper.getServerUrlAction(serverId),
                new ServerActions.SoftReboot(),
                RestUtils.getAuthHeaders(),
                new TypeReference<>() {
                });
    }

    @Override
    public void deleteServer(final String serverId) {
        LOGGER.debug(
                SERVER_ACTION_TEMPLATE,
                serverId,
                ServerActions.DeleteServer.class
        );
        restService.deleteRaw(settingsHelper.getServerUrl(serverId),
                new ServerActions.DeleteServer(),
                RestUtils.getAuthHeaders(),
                new TypeReference<>() {
                });
    }

    @Override
    public OpenstackServer createServer(final ServerCreateModel serverCreateModel) {
        LOGGER.debug("Start creation server:");
        LOGGER.debug(
                SERVER_INSTANTIATE,
                serverCreateModel.getName(),
                serverCreateModel.getZone(),
                serverCreateModel.getImageRef(),
                serverCreateModel.getFlavorRef(),
                serverCreateModel.getDiskConfig(),
                serverCreateModel.getNetworks()
        );
        Instance instance = restService.post(
                settingsHelper.getServersUrl(),
                serverCreateModel,
                RestUtils.getAuthHeaders(),
                new TypeReference<Instance>() {
                });

        LOGGER.debug(SERVER_TEMPLATE, instance.getOpenstackServer());
        return instance.getOpenstackServer();
    }
}
