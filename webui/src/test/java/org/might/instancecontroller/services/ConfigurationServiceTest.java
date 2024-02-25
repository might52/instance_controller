package org.might.instancecontroller.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.might.instancecontroller.dba.entity.*;
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

    @Autowired
    private FunctionService functionService;

    @Autowired
    private FlavorService flavorService;

    @Autowired
    private ServerService serverService;

    @Autowired
    private ImageService imageService;

    private static final String UPDATED_SCRIPTS = "updated_scripts";
    private static final String TEST_SCRIPTS = "test_scripts";
    private static final String CRE_CONF_TEMPLATE = "ID of created configuration: {} reference of created configuration: {}";

    private static final Logger LOGGER = LoggerFactory.getLogger(
            ConfigurationServiceTest.class
    );

    @After
    @Before
    public void cleanUpTable() {
        for (Server server:
                serverService.getAll()) {
            serverService.deleteServer(server);
        }

        for (Function function :
                functionService.getAll()) {
            functionService.deleteFunction(function);
        }

        for (Configuration configuration :
                configurationService.getAll()) {
            configurationService.deleteConfiguration(configuration);
        }

        for (Flavor flavor :
                flavorService.getAll()) {
            flavorService.deleteFlavor(flavor);
        }

        for (Image image:
                imageService.getAll()) {
            imageService.deleteImage(image);
        }
    }

    private Configuration prepareTestConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setScript(TEST_SCRIPTS);
        return configuration;
    }

    @Test
    public void canAddNewConfiguration() {
        Configuration configuration = prepareTestConfiguration();
        configurationService.saveConfiguration(configuration);
        LOGGER.debug(
                CRE_CONF_TEMPLATE,
                configuration.getId(),
                configuration.getScript()
        );

        Assert.assertEquals(prepareTestConfiguration(), configuration);
    }

    @Test
    public void canRemoveTestConfiguration() {
        Configuration configuration = prepareTestConfiguration();
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
        Configuration configuration = prepareTestConfiguration();
        configurationService.saveConfiguration(configuration);
        LOGGER.debug(
                CRE_CONF_TEMPLATE,
                configuration.getId(),
                configuration.getScript()
        );

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
        Configuration configuration = prepareTestConfiguration();
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
        Configuration configuration = prepareTestConfiguration();
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


}
