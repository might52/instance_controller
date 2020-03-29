package org.might.instancecontroller.services;

import org.might.instancecontroller.dba.entity.Function;

import java.util.List;
import java.util.Optional;

public interface FunctionService {
    Optional<Function> getFunctionById(Long id);
    void saveFunction(Function function);
    void deleteFunction(Function function);
    List<Function> getAll();
}
