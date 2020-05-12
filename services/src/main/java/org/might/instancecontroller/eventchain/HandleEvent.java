package org.might.instancecontroller.eventchain;

import org.might.instancecontroller.dba.entity.Event;

public interface HandleEvent {
    void handle(Event event);
}
