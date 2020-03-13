package com.might.instancecontroller.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.might.instancecontroller.annotations.RequireConnection;
import com.might.instancecontroller.models.servers.Server;
import com.might.instancecontroller.services.ComputeService;
import com.might.instancecontroller.services.ServerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("api/v1/instance")
public class InventoryController {

    private final ComputeService computeService;
    private final String serversResponse;
    private final ObjectMapper jsonSerializer;

    @Autowired
    public InventoryController(ComputeService computeService) throws IOException {
        this.computeService = computeService;
        this.serversResponse = Files.readString(
                Paths.get(
                        "D:\\repo\\git\\instance_controller\\services\\src" +
                                "\\main\\resources\\Jsons\\servers_response.json"),
                StandardCharsets.UTF_8
        );
        this.jsonSerializer = new ObjectMapper();
        this.jsonSerializer.enable(SerializationFeature.WRAP_ROOT_VALUE);
        this.jsonSerializer.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    @RequireConnection
    @GetMapping("/all/string")
    public String getServerListAsString() {
        return computeService.getListServer();
    }

    @RequireConnection
    @GetMapping("/all")
    public List<Server> getServerList() {
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
    public Server getInstance(
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
    @DeleteMapping("/{serverId}/delete")
    public void deleteServer(
            @PathVariable String serverId) {
        computeService.deleteServer(serverId);
    }
}
