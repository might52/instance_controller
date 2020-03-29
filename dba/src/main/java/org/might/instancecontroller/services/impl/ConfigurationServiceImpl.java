package org.might.instancecontroller.services.impl;

import org.might.instancecontroller.dba.entity.Configuration;
import org.might.instancecontroller.dba.repository.ConfigurationRepository;
import org.might.instancecontroller.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    private ConfigurationRepository configurationRepository;

    @Autowired
    public ConfigurationServiceImpl(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Override
    public Optional<Configuration> getConfigurationById(Long id) {
        return configurationRepository.findById(id);
    }

    @Override
    public void saveConfiguration(Configuration configuration) {
        configurationRepository.save(configuration);
    }

    @Override
    public void deleteConfiguration(Configuration configuration) {
        configurationRepository.delete(configuration);
    }

    @Override
    public List<Configuration> getAll() {
        return configurationRepository.findAll();
    }
}
