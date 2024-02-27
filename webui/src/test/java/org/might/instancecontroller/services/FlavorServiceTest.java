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
public class FlavorServiceTest {
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

    private static final String UPDATED_REF = "updatedReference";
    private static final String TEST_REF = "testReference";
    private static final String CRE_FLAVOR_TEMPLATE = "ID of created flavor: {} reference of created flavor: {}";

    private static final Logger LOGGER = LoggerFactory.getLogger(
            FlavorServiceTest.class
    );

    private Flavor prepareTestFlavor() {
        Flavor flavor = new Flavor();
        flavor.setReference(TEST_REF);
        return flavor;
    }

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

    @Test
    public void canAddNewFlavor() {
        Flavor flavor = prepareTestFlavor();
        flavorService.saveFlavor(flavor);
        LOGGER.debug(
                CRE_FLAVOR_TEMPLATE,
                flavor.getId(),
                flavor.getReference()
        );

        Assert.assertEquals(prepareTestFlavor(), flavor);
    }

    @Test
    public void canRemoveTestFlavor() {
        Flavor flavor = prepareTestFlavor();
        flavorService.saveFlavor(flavor);
        LOGGER.debug(
                CRE_FLAVOR_TEMPLATE,
                flavor.getId(),
                flavor.getReference()
        );

        Optional<Flavor> toBeDel = flavorService.getFlavorById(flavor.getId());
        toBeDel.ifPresent(value -> flavorService.deleteFlavor(value));
        Assert.assertEquals(0, flavorService.getAll().size());
    }

    @Test
    public void canUpdateFlavor() {
        Flavor flavor = prepareTestFlavor();
        flavorService.saveFlavor(flavor);
        LOGGER.debug(
                CRE_FLAVOR_TEMPLATE,
                flavor.getId(),
                flavor.getReference()
        );

        flavor.setReference(UPDATED_REF);
        flavorService.saveFlavor(flavor);
        LOGGER.debug(
                CRE_FLAVOR_TEMPLATE,
                flavor.getId(),
                flavor.getReference()
        );

        Assert.assertEquals(UPDATED_REF, flavor.getReference());
    }

    @Test
    public void canGetWholeFlavors() {
        Flavor image = prepareTestFlavor();
        flavorService.saveFlavor(image);
        LOGGER.debug(
                CRE_FLAVOR_TEMPLATE,
                image.getId(),
                image.getReference()
        );

        Assert.assertEquals(1 , flavorService.getAll().size());
    }

    @Test
    public void canGetTestFlavor() {
        Flavor image = prepareTestFlavor();
        flavorService.saveFlavor(image);
        Flavor receivedImage = new Flavor();
        if (flavorService.getFlavorById(image.getId()).isPresent()) {
            receivedImage = flavorService.getFlavorById(image.getId()).get();
        }

        LOGGER.debug(
                CRE_FLAVOR_TEMPLATE,
                image.getId(),
                image.getReference()
        );
        LOGGER.debug(
                CRE_FLAVOR_TEMPLATE,
                receivedImage.getId(),
                receivedImage.getReference()
        );

        Assert.assertEquals(image, receivedImage);
    }




}
