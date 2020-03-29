package org.might.instancecontroller.dba.repository;

import org.might.instancecontroller.dba.entity.Function;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunctionRepository extends JpaRepository<Function, Long> {
}
