package org.might.instancecontroller.services;

import org.might.instancecontroller.dba.entity.Configuration;

import java.util.List;
import java.util.Optional;

public interface ConfigurationService {
    Optional<Configuration> getConfigurationById(Long id);
    void saveConfiguration(Configuration configuration);
    void deleteConfiguration(Configuration configuration);
    List<Configuration> getAll();
}
