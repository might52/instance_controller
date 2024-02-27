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

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServerServiceTest {

    @Autowired
    private ServerService serverService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private FlavorService flavorService;

    @Autowired
    private FunctionService functionService;

    private static final String UPDATED_SERVER_NAME = "updated_server_name";
    private static final String UPDATED_SERVER_SERVER_ID = "updated_server_id";
    private static final Long UPDATED_MONITORING_ID = 10258L;

    private static final String TEST_SERVER_NAME = "test_server_name";
    private static final String TEST_SERVER_SERVER_ID = "test_server_id";
    private static final Long TEST_MONITORING_ID = 10257L;

    private static final String TEST_FUNCTION_NAME = "test_func_name";
    private static final String TEST_FUNCTION_DESC = "test_func_desc";
    private static final String TEST_FUNCTION_PREFIX = "test_func_prefix";
    private static final Boolean TEST_FUNCTION_SCALE_IN = false;
    private static final Boolean TEST_FUNCTION_SCALE_OUT = false;

    private static final String UPDATED_FUNCTION_NAME = "updated_func_name";
    private static final String UPDATED_FUNCTION_DESC = "updated_func_desc";
    private static final String UPDATED_FUNCTION_PREFIX = "updated_func_prefix";
    private static final Boolean UPDATED_FUNCTION_SCALE_IN = true;
    private static final Boolean UPDATED_FUNCTION_SCALE_OUT = true;

    private static final String TEST_CONF_SCRIPTS = "test_scripts";
    private static final String TEST_IMAGE_REF = "test_image_ref";
    private static final String TEST_FLAVOR_REF = "test_flavor_ref";

    private static final String SERVER_TEMPLATE =
            "Server ID: {}, " +
            "name: {}, " +
            "serverId: {}, " +
            "monitoringId: {}" +
            "function: {}";

    private static final Logger LOGGER = LoggerFactory.getLogger(
            ServerServiceTest.class
    );

    @After
    @Before
    public void cleanUpTable() {
        for (Server server :
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

    private Server prepareTestServer() {
        Server server = new Server();
        server.setName(TEST_SERVER_NAME);
        server.setServerId(TEST_SERVER_SERVER_ID);
        server.setFunction(prepareTestFunction());
        server.setMonitoringId(TEST_MONITORING_ID);
        return server;
    }

    private Function prepareTestFunction() {
        Function function = new Function();
        function.setName(TEST_FUNCTION_NAME);
        function.setDescription(TEST_FUNCTION_DESC);
        function.setPrefix(TEST_FUNCTION_PREFIX);
        Flavor flavor = prepareTestFlavor();
        function.setFlavor(flavor);
        Configuration configuration = prepareConfiguration();
        function.setConfiguration(configuration);
        Image image = prepareTestImage();
        function.setImage(image);
        function.setScaleInAbility(TEST_FUNCTION_SCALE_IN);
        function.setScaleOutAbility(TEST_FUNCTION_SCALE_OUT);
        if (functionService
                .getAll()
                .stream()
                .anyMatch(el -> el.equals(function))){
            return functionService
                    .getAll()
                    .stream()
                    .filter(el -> el.equals(function))
                    .findFirst()
                    .get();
        } else {
            functionService.saveFunction(function);
        }

        return function;
    }

    private Function prepareUpdatedTestFunction() {
        Function function = new Function();
        function.setName(UPDATED_FUNCTION_NAME);
        function.setDescription(UPDATED_FUNCTION_DESC);
        function.setPrefix(UPDATED_FUNCTION_PREFIX);
        Flavor flavor = prepareTestFlavor();
        function.setFlavor(flavor);
        Configuration configuration = prepareConfiguration();
        function.setConfiguration(configuration);
        Image image = prepareTestImage();
        function.setImage(image);
        function.setScaleInAbility(UPDATED_FUNCTION_SCALE_IN);
        function.setScaleOutAbility(UPDATED_FUNCTION_SCALE_OUT);
        if (functionService
                .getAll()
                .stream()
                .anyMatch(el -> el.equals(function))){
            return functionService
                    .getAll()
                    .stream()
                    .filter(el -> el.equals(function))
                    .findFirst()
                    .get();
        } else {
            functionService.saveFunction(function);
        }

        return function;
    }

    private Configuration prepareConfiguration() {
        final Configuration configuration = new Configuration();
        configuration.setScript(TEST_CONF_SCRIPTS);
        if (configurationService
                .getAll()
                .stream()
                .anyMatch(el -> el.equals(configuration))){
            return configurationService
                    .getAll()
                    .stream()
                    .filter(el -> el.equals(configuration))
                    .findFirst()
                    .get();
        } else {
            configurationService.saveConfiguration(configuration);
        }

        return configuration;
    }

    private Image prepareTestImage() {
        final Image image = new Image();
        image.setReference(TEST_IMAGE_REF);
        if (imageService
                .getAll()
                .stream()
                .anyMatch(el -> el.equals(image))){
            return imageService
                    .getAll()
                    .stream()
                    .filter(el -> el.equals(image))
                    .findFirst()
                    .get();
        } else {
            imageService.saveImage(image);
        }

        return image;
    }

    private Flavor prepareTestFlavor() {
        final Flavor flavor = new Flavor();
        flavor.setReference(TEST_FLAVOR_REF);
        if (flavorService
                .getAll()
                .stream()
                .anyMatch(el -> el.equals(flavor))){
            return flavorService
                    .getAll()
                    .stream()
                    .filter(el -> el.equals(flavor))
                    .findFirst()
                    .get();
        } else {
            flavorService.saveFlavor(flavor);
        }

        return flavor;
    }

    @Test
    public void canAddNewServer() {
        Server server = prepareTestServer();
        serverService.saveServer(server);
        LOGGER.debug(SERVER_TEMPLATE,
                server.getId(),
                server.getName(),
                server.getServerId(),
                server.getMonitoringId(),
                server.getFunction().getId());
        Assert.assertEquals(prepareTestServer(), server);
    }

    @Test
    public void canChangeName() {
        Server server = prepareTestServer();
        serverService.saveServer(server);
        LOGGER.debug(SERVER_TEMPLATE,
                server.getId(),
                server.getName(),
                server.getServerId(),
                server.getMonitoringId(),
                server.getFunction().getId());
        server.setName(UPDATED_SERVER_NAME);
        serverService.saveServer(server);
        LOGGER.debug(SERVER_TEMPLATE,
                server.getId(),
                server.getName(),
                server.getServerId(),
                server.getMonitoringId(),
                server.getFunction().getId());
        Assert.assertEquals(UPDATED_SERVER_NAME, server.getName());
    }

    @Test
    public void canChangeServerId() {
        Server server = prepareTestServer();
        serverService.saveServer(server);
        LOGGER.debug(SERVER_TEMPLATE,
                server.getId(),
                server.getName(),
                server.getServerId(),
                server.getMonitoringId(),
                server.getFunction().getId());
        server.setServerId(UPDATED_SERVER_SERVER_ID);
        serverService.saveServer(server);
        LOGGER.debug(SERVER_TEMPLATE,
                server.getId(),
                server.getName(),
                server.getServerId(),
                server.getMonitoringId(),
                server.getFunction().getId());
        Assert.assertEquals(UPDATED_SERVER_SERVER_ID, server.getServerId());
    }

    @Test
    public void canChangeMonitoringId() {
        Server server = prepareTestServer();
        serverService.saveServer(server);
        LOGGER.debug(SERVER_TEMPLATE,
                server.getId(),
                server.getName(),
                server.getServerId(),
                server.getMonitoringId(),
                server.getFunction().getId());
        server.setMonitoringId(UPDATED_MONITORING_ID);
        serverService.saveServer(server);
        LOGGER.debug(SERVER_TEMPLATE,
                server.getId(),
                server.getName(),
                server.getServerId(),
                server.getMonitoringId(),
                server.getFunction().getId());
        Assert.assertEquals(UPDATED_MONITORING_ID, server.getMonitoringId());
    }

    @Test
    public void canChangeFunction() {
        Server server = prepareTestServer();
        serverService.saveServer(server);
        LOGGER.debug(SERVER_TEMPLATE,
                server.getId(),
                server.getName(),
                server.getServerId(),
                server.getMonitoringId(),
                server.getFunction().getId());
        server.setFunction(prepareUpdatedTestFunction());
        serverService.saveServer(server);
        LOGGER.debug(SERVER_TEMPLATE,
                server.getId(),
                server.getName(),
                server.getServerId(),
                server.getMonitoringId(),
                server.getFunction().getId());
        Assert.assertEquals(UPDATED_FUNCTION_NAME, server.getFunction().getName());
    }

    @Test
    public void canDeleteServer() {
        Server server = prepareTestServer();
        serverService.saveServer(server);
        LOGGER.debug(SERVER_TEMPLATE,
                server.getId(),
                server.getName(),
                server.getServerId(),
                server.getMonitoringId(),
                server.getFunction().getId());
        serverService.saveServer(server);

        if (serverService.getServerById(server.getId()).isPresent()) {
            serverService.deleteServer(
                    serverService.getServerById(server.getId()).get()
            );
        }

        Assert.assertEquals(0, serverService.getAll().size());
    }
}
