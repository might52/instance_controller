package org.might.instancecontroller.dba.repository;

import org.might.instancecontroller.dba.entity.Flavor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlavorRepository extends JpaRepository<Flavor, Long> {
}
