package com.might.instancecontroller.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.might.instancecontroller.annotations.RequireConnection;
import com.might.instancecontroller.models.servers.Server;
import com.might.instancecontroller.services.ComputeService;
import com.might.instancecontroller.services.ServerStatus;
import com.might.instancecontroller.services.transport.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("api/v1/instance")
public class InventoryController {

    private ComputeService computeService;
    private String serversReponse;
    private ObjectMapper jsonSerializer;

    @Autowired
    public InventoryController(ComputeService computeService) throws IOException {
        this.computeService = computeService;
        this.serversReponse = Files.readString(
                Paths.get(
                        "D:\\repos\\instance_controller\\services\\src\\" +
                                "main\\resources\\Jsons\\servers_response.json"),
                StandardCharsets.UTF_8
        );
        this.jsonSerializer = new ObjectMapper();
        this.jsonSerializer.enable(SerializationFeature.WRAP_ROOT_VALUE);
        this.jsonSerializer.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

    }

    @RequireConnection
    @GetMapping("/all/string")
    public String getServerListAsString(HttpServletResponse response) {
        RestUtils.addAccessControlAllowOriginHeader(response);
        return computeService.getListServer();
    }

    @RequireConnection
    @GetMapping("/all")
    public List<Server> getServerList(HttpServletResponse response) throws IOException {
        RestUtils.addAccessControlAllowOriginHeader(response);
        return computeService.getServerList();

    }


    @GetMapping("/all_stub")
    public String getServerListStub(HttpServletResponse response) throws IOException {
        RestUtils.addAccessControlAllowOriginHeader(response);
        return  this.serversReponse;
    }


    @RequireConnection
    @GetMapping("/status/{serverId}")
    public ServerStatus getServerStatus(
            HttpServletResponse response,
            @PathVariable String serverId) {
        RestUtils.addAccessControlAllowOriginHeader(response);
        String value = computeService.getServerStatus(serverId);
        return ServerStatus.getServerStatus(value);
    }

    @RequireConnection
    @GetMapping("/name/{serverId}")
    public String getInstanceName(
            HttpServletResponse response,
            @PathVariable String serverId) {
        RestUtils.addAccessControlAllowOriginHeader(response);
        return computeService.getServerName(serverId);
    }

    @RequireConnection
    @GetMapping("/{serverId}")
    public Server getInstance(
            HttpServletResponse response,
            @PathVariable String serverId) {
        RestUtils.addAccessControlAllowOriginHeader(response);
        return computeService.getServer(serverId);
    }
}
