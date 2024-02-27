/*
 * MIT License
 *
 * Copyright (c) 2024 Andrei F._
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.might.instancecontroller.controllers;

import org.might.instancecontroller.annotations.RequireConnection;
import org.might.instancecontroller.models.servers.OpenstackServer;
import org.might.instancecontroller.services.ComputeService;
import org.might.instancecontroller.services.ServerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("api/v1/instance")
public class InventoryController {

    private final ComputeService computeService;
    private final String serversResponse;

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
