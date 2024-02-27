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
public class ImageServiceTest {
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
    private static final String CRE_IMG_TEMPLATE = "ID of created image: {} reference of created image: {}";

    private static final Logger LOGGER = LoggerFactory.getLogger(
            ImageServiceTest.class
    );

    private Image prepareTestImage() {
        Image image = new Image();
        image.setReference(TEST_REF);
        return image;
    }

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

    @Test
    public void canAddNewImage() {
        Image image = prepareTestImage();
        imageService.saveImage(image);
        LOGGER.debug(
                CRE_IMG_TEMPLATE,
                image.getId(),
                image.getReference()
        );

        Assert.assertEquals(prepareTestImage(), image);
    }

    @Test
    public void canRemoveTestImage() {
        Image image = prepareTestImage();
        imageService.saveImage(image);
        LOGGER.debug(
                CRE_IMG_TEMPLATE,
                image.getId(),
                image.getReference()
        );

        Optional<Image> toBeDel = imageService.getImageById(image.getId());
        toBeDel.ifPresent(value -> imageService.deleteImage(value));
        Assert.assertEquals(0, imageService.getAll().size());
    }

    @Test
    public void canUpdateImage() {
        Image image = prepareTestImage();
        imageService.saveImage(image);
        LOGGER.debug(
                CRE_IMG_TEMPLATE,
                image.getId(),
                image.getReference()
        );

        image.setReference(UPDATED_REF);
        imageService.saveImage(image);
        LOGGER.debug(
                CRE_IMG_TEMPLATE,
                image.getId(),
                image.getReference()
        );

        Assert.assertEquals(UPDATED_REF, image.getReference());
    }

    @Test
    public void canGetWholeImages() {
        Image image = prepareTestImage();
        imageService.saveImage(image);
        LOGGER.debug(
                CRE_IMG_TEMPLATE,
                image.getId(),
                image.getReference()
        );

        Assert.assertEquals(1 , imageService.getAll().size());
    }

    @Test
    public void canGetTestImage() {
        Image image = prepareTestImage();
        imageService.saveImage(image);
        Image receivedImage = new Image();
        if (imageService.getImageById(image.getId()).isPresent()) {
            receivedImage = imageService.getImageById(image.getId()).get();
        }

        LOGGER.debug(
                CRE_IMG_TEMPLATE,
                image.getId(),
                image.getReference()
        );
        LOGGER.debug(
                CRE_IMG_TEMPLATE,
                receivedImage.getId(),
                receivedImage.getReference()
        );

        Assert.assertEquals(image, receivedImage);
    }

}
