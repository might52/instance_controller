package org.might.instancecontroller.controllers;

import org.might.instancecontroller.annotations.RequireConnection;
import org.might.instancecontroller.models.servers.OpenstackServer;
import org.might.instancecontroller.services.ComputeService;
import org.might.instancecontroller.services.ServerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("api/v1/instance")
public class InventoryController {

    private final ComputeService computeService;
    private final String serversResponse;

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    public InventoryController(ComputeService computeService) {
        this.computeService = computeService;
        String stubServerResponse = "";
        try {
            stubServerResponse = Files.readString(
                    Paths.get(
                            Paths.get("").toAbsolutePath() +
                                    "/services/src/main/resources/Jsons/servers_response.json"
                    ), StandardCharsets.UTF_8
            );
        } catch (Exception ignoredException) {
        }
        this.serversResponse = stubServerResponse;
    }

    @RequireConnection
    @GetMapping("/all/string")
    public String getServerListAsString() {
        return computeService.getListServer();
    }

    @RequireConnection
    @GetMapping("/all")
    public List<OpenstackServer> getServerList() {
        return computeService.getServerList();
    }

    @GetMapping("/all_stub")
    public String getServerListStub() {
        return this.serversResponse;
    }

    @RequireConnection
    @GetMapping("/status/{serverId}")
    public ServerStatus getServerStatus(
            @PathVariable String serverId) {
        String value = computeService.getServerStatus(serverId);
        return ServerStatus.getServerStatus(value);
    }

    @RequireConnection
    @GetMapping("/name/{serverId}")
    public String getInstanceName(
            @PathVariable String serverId) {
        return computeService.getServerName(serverId);
    }

    @RequireConnection
    @GetMapping("/{serverId}")
    public OpenstackServer getInstance(
            @PathVariable String serverId) {
        return computeService.getServer(serverId);
    }

    @RequireConnection
    @PostMapping("/{serverId}/stop")
    public void stopServer(
            @PathVariable String serverId) {
        computeService.stopServer(serverId);
    }

    @RequireConnection
    @PostMapping("/{serverId}/start")
    public void startServer(
            @PathVariable String serverId) {
        computeService.startServer(serverId);
    }

    @RequireConnection
    @PostMapping("/{serverId}/hardreboot")
    public void hardReboot(
            @PathVariable String serverId) {
        computeService.hardReboot(serverId);
    }

    @RequireConnection
    @PostMapping("/{serverId}/softreboot")
    public void softReboot(
            @PathVariable String serverId) {
        computeService.softReboot(serverId);
    }

    @RequireConnection
    @PostMapping("/{serverId}/pause")
    public void pause(
            @PathVariable String serverId) {
        computeService.pauseServer(serverId);
    }

    @RequireConnection
    @PostMapping("/{serverId}/unpause")
    public void unPause(
            @PathVariable String serverId) {
        computeService.unPauseServer(serverId);
    }

    @RequireConnection
    @DeleteMapping("/{serverId}/delete")
    public void deleteServer(
            @PathVariable String serverId) {
        computeService.deleteServer(serverId);
    }
}
