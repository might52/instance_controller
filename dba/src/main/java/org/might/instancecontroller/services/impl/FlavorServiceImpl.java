package org.might.instancecontroller.services.impl;

import org.might.instancecontroller.dba.entity.Flavor;
import org.might.instancecontroller.dba.repository.FlavorRepository;
import org.might.instancecontroller.services.FlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlavorServiceImpl implements FlavorService {

    private FlavorRepository flavorRepository;

    @Autowired
    public FlavorServiceImpl(FlavorRepository flavorRepository) {
        this.flavorRepository = flavorRepository;
    }

    @Override
    public Optional<Flavor> getFlavorById(Long id) {
        return flavorRepository.findById(id);
    }

    @Override
    public void saveFlavor(Flavor flavor) {
        flavorRepository.save(flavor);
    }

    @Override
    public void deleteFlavor(Flavor flavor) {
        flavorRepository.delete(flavor);
    }

    @Override
    public List<Flavor> getAll() {
        return flavorRepository.findAll();
    }
}
