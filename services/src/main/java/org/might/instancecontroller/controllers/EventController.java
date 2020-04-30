package org.might.instancecontroller.controllers;

import org.might.instancecontroller.dba.entity.Event;
import org.might.instancecontroller.models.monitoring.NotificationModel;
import org.might.instancecontroller.services.EventService;
import org.might.instancecontroller.utils.MonitoringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/event")
public class EventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    /**
     *   "status": "PROBLEM",
     *   "name": "Unavailable by ICMP ping",
     *   "time": "20:50:30",
     *   "subject": "problem|Unavailable by ICMP ping|192.168.20.107|PROBLEM|High",
     *   "host_ip": "192.168.20.107",
     *   "date": "2020.04.28",
     *   "host_name": "webServerFunction_webserv_1",
     *   "ack_status": "No",
     *   "problemId": "569",
     *   "active": true,
     *   "host_desc": "c5d8e3dd-5ffa-4ffa-b15c-7b9683ff14e1",
     *   "severity": "High"
     */
    private static final String EVENT_ATTRS = "status: {}" +
            "name: {}" +
            "time: {}" +
            "subject/empty: {}" +
            "host_ip: {}" +
            "date: {}" +
            "host_name: {}" +
            "ack_status: {}" +
            "problemId: {}" +
            "active: {}" +
            "host_desc/serverId: {}" +
            "severity: {}";
    private static final String EVENT_TEMPLATE = "Get notification model: " + EVENT_ATTRS;
    private static final String EVENT_SAVE_TEMPLATE = "Save event model: " + EVENT_ATTRS;
    private static final String EMPTY_STRING = "";

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
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
        if (eventService
                .getAll()
                .stream()
                .noneMatch(event::equals)) {
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
                    event.getSeverity());
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
