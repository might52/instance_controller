package org.might.instancecontroller.services;

import org.might.instancecontroller.dba.entity.Flavor;

import java.util.List;
import java.util.Optional;

public interface FlavorService {
    Optional<Flavor> getFlavorById(Long id);
    void saveFlavor(Flavor flavor);
    void deleteFlavor(Flavor flavor);
    List<Flavor> getAll();
}
