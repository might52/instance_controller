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

    private EventService eventService;

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
