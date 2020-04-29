package org.might.instancecontroller.services;

import org.might.instancecontroller.dba.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    Optional<Event> getEventById(Long id);
    void saveEvent(Event event);
    void deleteEvent(Event event);
    List<Event> getAll();
}
