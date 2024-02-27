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
import org.might.instancecontroller.dba.entity.Event;
import org.might.instancecontroller.eventchain.HandleEvent;
import org.might.instancecontroller.models.monitoring.NotificationModel;
import org.might.instancecontroller.utils.MonitoringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/v1/event")
public class EventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

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
    private static final String EVENT_TEMPLATE = "Get notification model: " + EVENT_ATTRS;

    @Autowired
    private List<HandleEvent> handlers;

    @RequireConnection
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void getEvent(@RequestBody NotificationModel notificationModel) {
        LOGGER.debug(
                EVENT_TEMPLATE,
                notificationModel.getStatus(),
                notificationModel.getName(),
                notificationModel.getTime(),
                notificationModel.getSubject(),
                notificationModel.getHostIp(),
                notificationModel.getDate(),
                notificationModel.getHostName(),
                notificationModel.getAckStatus(),
                notificationModel.getProblemId(),
                notificationModel.getActive(),
                notificationModel.getHostDesc(),
                notificationModel.getSeverity()
        );
        Event event = MonitoringHelper.getEventByNotification(notificationModel);
        handlers.forEach(handleEvent -> handleEvent.handle(event));
    }

}
