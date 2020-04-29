package org.might.instancecontroller.dba.repository;

import org.might.instancecontroller.dba.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}