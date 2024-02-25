package org.might.instancecontroller.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.might.instancecontroller.dba.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Time;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @After
    @Before
    public void cleanUpTable() {
        for (Event event :
                eventService.getAll()) {
            eventService.deleteEvent(event);
        }
    }

    private static final String NAME = "Unavailable by ICMP ping";
    private static final String HOST_IP = "192.168.20.107";
    private static final String HOST_NAME = "webServerFunction_webserv_1";
    private static final String ACK_STATUS = "No";
    private static final String PROBLEM_ID = "569";
    private static final boolean ACTIVE = true;
    private static final boolean ACTIVE_SOLVED = false;
    private static final String SERVER_ID = "c5d8e3dd-5ffa-4ffa-b15c-7b9683ff14e1";
    private static final String SEVERITY = "High";
    private static final String STATUS = "PROBLEM";
    private static final String STATUS_SOLVED = "RESOLVED";
    private static final Time TIME = Time.valueOf("20:50:30");
    private static final Time RECOVERY_TIME = Time.valueOf("20:51:30");
    private static final Date DATE = Date.valueOf("2020.04.28".replace(".","-"));
    private static final Date RECOVERY_DATE = Date.valueOf("2020.04.28".replace(".","-"));

    private Event getTemplateEvent(boolean resolved) {
        Event event = new Event();
        event.setName(NAME);
        event.setAckStatus(ACK_STATUS);
        event.setHostIp(HOST_IP);
        event.setHostName(HOST_NAME);
        event.setProblemId(PROBLEM_ID);
        event.setActive(resolved ? ACTIVE_SOLVED : ACTIVE);
        event.setServerId(SERVER_ID);
        event.setSeverity(SEVERITY);
        event.setStatus(resolved ? STATUS_SOLVED : STATUS);
        event.setTime(resolved ? RECOVERY_TIME : TIME);
        event.setDate(resolved ? RECOVERY_DATE : DATE);
        return event;
    }

    @Test
    public void canAddNewEvent() {
        Event event = getTemplateEvent(false);
        eventService.saveEvent(event);
        Assert.assertEquals(getTemplateEvent(false), event);
    }

    @Test
    public void canUpdateEvent() {
        Event event = getTemplateEvent(false);
        eventService.saveEvent(event);
        event.setRecoveryDate(RECOVERY_DATE);
        event.setRecoveryTime(RECOVERY_TIME);
        event.setActive(ACTIVE_SOLVED);
        event.setStatus(STATUS_SOLVED);
        eventService.saveEvent(event);
        Assert.assertEquals(getTemplateEvent(true), event);
    }

    @Test
    public void canDeleteEvent() {
        Event event = getTemplateEvent(false);
        eventService.saveEvent(event);
        eventService.saveEvent(getTemplateEvent(true));
        eventService.deleteEvent(eventService.getEventById(event.getId()).orElse(null));
        Assert.assertEquals(getTemplateEvent(true), eventService.getAll().get(0));
    }

    @Test
    public void canGetWholeEvents() {
        Event event = getTemplateEvent(false);
        eventService.saveEvent(event);
        Assert.assertEquals(1, eventService.getAll().size());
    }
}
