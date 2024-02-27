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
import org.might.instancecontroller.services.EventService;
import org.might.instancecontroller.utils.MonitoringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Order(1)
@Component
public class DBHandlerEventChain implements HandleEvent {

    private final EventService eventService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DBHandlerEventChain.class);
    private static final String EMPTY_STRING = "";
    private static final String EVENT_ATTRS = "status: {}" +
            " name: {}" +
            " time: {}" +
            " subject/empty: {}" +
            " host_ip: {}" +
            " date: {}" +
            " host_name: {}" +
            " ack_status: {}" +
            " problemId: {}" +
            " active: {}" +
            " host_desc/serverId: {}" +
            " severity: {}";
    private static final String EVENT_SAVE_TEMPLATE = "Save event model: " + EVENT_ATTRS;

    @Autowired
    public DBHandlerEventChain(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void handle(Event event) {
        if (eventService.getAll().stream().noneMatch(event::equals)) {
            LOGGER.debug(EVENT_SAVE_TEMPLATE,
                    event.getStatus(),
                    event.getName(),
                    event.getTime(),
                    EMPTY_STRING,
                    event.getHostIp(),
                    event.getDate(),
                    event.getHostName(),
                    event.getAckStatus(),
                    event.getProblemId(),
                    event.getActive(),
                    event.getServerId(),
                    event.getSeverity()
            );
            eventService.saveEvent(event);
            return;
        }

        List<Event> eventList = eventService
                .getAll()
                .stream()
                .filter(event::equals)
                .collect(Collectors.toList());

        if (eventList.size() == 1) {
            event = MonitoringHelper.compareAndUpdate(eventList.get(0), event);
            eventService.saveEvent(event);
            return;
        }

        if (eventList.size() > 1) {
            LOGGER.error("Count of events are exceeded. " +
                            "Event can't be saved or updated." + EVENT_ATTRS,
                    event.getStatus(),
                    event.getName(),
                    event.getTime(),
                    EMPTY_STRING,
                    event.getHostIp(),
                    event.getDate(),
                    event.getHostName(),
                    event.getAckStatus(),
                    event.getProblemId(),
                    event.getActive(),
                    event.getServerId(),
                    event.getSeverity()
            );

            throw new RuntimeException("Count of events are exceeded.");
        }
    }

}
