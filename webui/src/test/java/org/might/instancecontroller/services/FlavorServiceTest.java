package org.might.instancecontroller.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.might.instancecontroller.dba.entity.Flavor;
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
    private FlavorService flavorService;

    private static final String UPDATED_REF = "updatedReference";
    private static final String TEST_REF = "testReference";
    private static final String CRE_FLAVOR_TEMPLATE = "ID of created flavor: {} reference of created flavor: {}";

    private static final Logger LOGGER = LoggerFactory.getLogger(
            FlavorServiceTest.class
    );

    @Before
    public void cleanUpTable() {
        for (Flavor flavor :
                flavorService.getAll()) {
            flavorService.deleteFlavor(flavor);
        }
    }

    @Test
    public void canAddNewFlavor() {
        Flavor flavor = prepateTestFlavor();
        flavorService.saveFlavor(flavor);
        LOGGER.debug(
                CRE_FLAVOR_TEMPLATE,
                flavor.getId(),
                flavor.getReference()
        );

        Assert.assertEquals(prepateTestFlavor(), flavor);
    }

    @Test
    public void canRemoveTestFlavor() {
        Flavor flavor = prepateTestFlavor();
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
        Flavor flavor = prepateTestFlavor();
        flavorService.saveFlavor(flavor);
        LOGGER.debug(
                CRE_FLAVOR_TEMPLATE,
                flavor.getId(),
                flavor.getReference()
        );

        Optional<Flavor> toBeUpdated = flavorService.getFlavorById(flavor.getId());
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
        Flavor image = prepateTestFlavor();
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
        Flavor image = prepateTestFlavor();
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

    private Flavor prepateTestFlavor() {
        Flavor flavor = new Flavor();
        flavor.setReference(TEST_REF);
        return flavor;
    }


}
