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
import org.springframework.web.bind.annotation.*;

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
