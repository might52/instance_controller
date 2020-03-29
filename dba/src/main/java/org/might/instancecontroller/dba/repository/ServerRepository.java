package org.might.instancecontroller.dba.repository;

import org.might.instancecontroller.dba.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, Long> {
}
