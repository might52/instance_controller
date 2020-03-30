package org.might.instancecontroller.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.might.instancecontroller.dba.entity.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConfigurationServiceTest {
    @Autowired
    private ConfigurationService configurationService;

    private static final String UPDATED_SCRIPTS = "updated_scripts";
    private static final String TEST_SCRIPTS = "test_scripts";
    private static final String CRE_CONF_TEMPLATE = "ID of created configuration: {} reference of created configuration: {}";

    private static final Logger LOGGER = LoggerFactory.getLogger(
            ConfigurationServiceTest.class
    );

    @Before
    public void cleanUpTable() {
        for (Configuration configuration:
                configurationService.getAll()) {
            configurationService.deleteConfiguration(configuration);
        }
    }

    @Test
    public void canAddNewConfiguration() {
        Configuration configuration = prepateTestConfiguration();
        configurationService.saveConfiguration(configuration);
        LOGGER.debug(
                CRE_CONF_TEMPLATE,
                configuration.getId(),
                configuration.getScript()
        );

        Assert.assertEquals(prepateTestConfiguration(), configuration);
    }

    @Test
    public void canRemoveTestConfiguration() {
        Configuration configuration = prepateTestConfiguration();
        configurationService.saveConfiguration(configuration);
        LOGGER.debug(
                CRE_CONF_TEMPLATE,
                configuration.getId(),
                configuration.getScript()
        );

        Optional<Configuration> toBeDel = configurationService.getConfigurationById(configuration.getId());
        toBeDel.ifPresent(value -> configurationService.deleteConfiguration(value));
        Assert.assertEquals(0, configurationService.getAll().size());
    }

    @Test
    public void canUpdateConfiguration() {
        Configuration configuration = prepateTestConfiguration();
        configurationService.saveConfiguration(configuration);
        LOGGER.debug(
                CRE_CONF_TEMPLATE,
                configuration.getId(),
                configuration.getScript()
        );

        Optional<Configuration> toBeUpdated = configurationService.getConfigurationById(configuration.getId());
        configuration.setScript(UPDATED_SCRIPTS);
        configurationService.saveConfiguration(configuration);
        LOGGER.debug(
                CRE_CONF_TEMPLATE,
                configuration.getId(),
                configuration.getScript()
        );

        Assert.assertEquals(UPDATED_SCRIPTS, configuration.getScript());
    }

    @Test
    public void canGetWholeConfiguration() {
        Configuration configuration = prepateTestConfiguration();
        configurationService.saveConfiguration(configuration);
        LOGGER.debug(
                CRE_CONF_TEMPLATE,
                configuration.getId(),
                configuration.getScript()
        );

        Assert.assertEquals(1 , configurationService.getAll().size());
    }

    @Test
    public void canGetTestConfiguration() {
        Configuration configuration = prepateTestConfiguration();
        configurationService.saveConfiguration(configuration);
        Configuration receivedImage = new Configuration();
        if (configurationService.getConfigurationById(configuration.getId()).isPresent()) {
            receivedImage = configurationService.getConfigurationById(configuration.getId()).get();
        }

        LOGGER.debug(
                CRE_CONF_TEMPLATE,
                configuration.getId(),
                configuration.getScript()
        );
        LOGGER.debug(
                CRE_CONF_TEMPLATE,
                receivedImage.getId(),
                receivedImage.getScript()
        );

        Assert.assertEquals(configuration, receivedImage);
    }

    private Configuration prepateTestConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setScript(TEST_SCRIPTS);
        return configuration;
    }


}
