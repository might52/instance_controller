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
package org.might.instancecontroller.eventchain;

import org.might.instancecontroller.dba.entity.Event;
import org.might.instancecontroller.dba.entity.Server;
import org.might.instancecontroller.services.InstanceService;
import org.might.instancecontroller.services.ServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Order(2)
@Component
public class AnalyzeHandlerEventChain implements HandleEvent {

    private static final String HIGH_SEVERITY = "High";
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyzeHandlerEventChain.class);
    private final InstanceService instanceService;
    private final ServerService serverService;

    @Autowired
    public AnalyzeHandlerEventChain(InstanceService instanceService,
                                    ServerService serverService) {
        this.instanceService = instanceService;
        this.serverService = serverService;
    }

    @Override
    public void handle(Event event) {
        if (event.getActive() && event.getSeverity().equals(HIGH_SEVERITY)) {
            LOGGER.debug("Event has an active state and high severity");
            LOGGER.debug("Run recreation process for event. " +
                            "Name: {}, " +
                            "Problem Id: {}, " +
                            "Severity: {}, " +
                            "Server id: {}, " +
                            "Host ip: {}, " +
                            "Host name: {}",
                    event.getName(),
                    event.getProblemId(),
                    event.getSeverity(),
                    event.getServerId(),
                    event.getHostIp(),
                    event.getHostName()
            );
            try {
                Server server = null;
                if (serverService.getAll().stream().anyMatch(elem ->
                        elem.getServerId().equals(event.getServerId()))) {
                    server = serverService.getAll().stream().filter(elem ->
                            elem.getServerId().equals(event.getServerId())).findFirst().get();
                }

                if (Objects.nonNull(server)) {
                    instanceService.reInstantiateByServerId(server.getId());
                }

            } catch (InterruptedException ex) {
                LOGGER.debug("Something went wrong: {} ", ex.getLocalizedMessage());
            }

        }

        LOGGER.debug("Event has an inactive state (give an just state)");
    }
}
