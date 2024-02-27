/*
 * MIT License
 *
 * Copyright (c) 2024 Andrei F._
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.might.instancecontroller.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.might.instancecontroller.dba.entity.Configuration;
import org.might.instancecontroller.dba.entity.Flavor;
import org.might.instancecontroller.dba.entity.Function;
import org.might.instancecontroller.dba.entity.Image;
import org.might.instancecontroller.dba.entity.Server;
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
