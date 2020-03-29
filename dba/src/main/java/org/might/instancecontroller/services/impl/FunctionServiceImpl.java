package org.might.instancecontroller.services.impl;

import org.might.instancecontroller.dba.entity.Function;
import org.might.instancecontroller.dba.repository.FunctionRepository;
import org.might.instancecontroller.services.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FunctionServiceImpl implements FunctionService {

    private FunctionRepository functionRepository;

    @Autowired
    public FunctionServiceImpl(FunctionRepository functionRepository) {
        this.functionRepository = functionRepository;
    }

    @Override
    public Optional<Function> getFunctionById(Long id) {
        return functionRepository.findById(id);
    }

    @Override
    public void saveFunction(Function function) {
        functionRepository.save(function);
    }

    @Override
    public void deleteFunction(Function function) {
        functionRepository.delete(function);
    }

    @Override
    public List<Function> getAll() {
        return functionRepository.findAll();
    }
}
