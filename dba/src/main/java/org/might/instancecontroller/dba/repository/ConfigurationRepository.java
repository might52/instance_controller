package org.might.instancecontroller.dba.repository;

import org.might.instancecontroller.dba.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
}
