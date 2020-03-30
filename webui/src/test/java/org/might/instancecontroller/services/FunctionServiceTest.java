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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class FunctionServiceTest {

    @Autowired
    private FunctionService functionService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private FlavorService flavorService;

    @Autowired
    private ImageService imageService;

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

    private static final String FUNCTION_TEMPLATE =
            "Function ID: {}, " +
            "name: {}, " +
            "description: {}, " +
            "prefix: {}, " +
            "scaleInAbility: {}, " +
            "scaleOutAbility: {}";

    private static final String TEST_CONF_SCRIPTS = "test_scripts";
    private static final String TEST_IMAGE_REF = "test_image_ref";
    private static final String TEST_FLAVOR_REF = "test_flavor_ref";

    private static final Logger LOGGER = LoggerFactory.getLogger(
            ServerServiceTest.class
    );

    @After
    @Before
    public void cleanUpTable() {
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
    public void canAddNewFunction() {
        Function function = prepareTestFunction();
        functionService.saveFunction(function);
        LOGGER.debug(FUNCTION_TEMPLATE,
                function.getId(),
                function.getName(),
                function.getDescription(),
                function.getPrefix(),
                function.getScaleInAbility(),
                function.getScaleOutAbility());
        Assert.assertEquals(prepareTestFunction(), function);
    }

    @Test
    public void canChangeName() {
        Function function = prepareTestFunction();
        functionService.saveFunction(function);
        LOGGER.debug(FUNCTION_TEMPLATE,
                function.getId(),
                function.getName(),
                function.getDescription(),
                function.getPrefix(),
                function.getScaleInAbility(),
                function.getScaleOutAbility());
        function.setName(UPDATED_FUNCTION_NAME);
        functionService.saveFunction(function);
        LOGGER.debug(FUNCTION_TEMPLATE,
                function.getId(),
                function.getName(),
                function.getDescription(),
                function.getPrefix(),
                function.getScaleInAbility(),
                function.getScaleOutAbility());
        Assert.assertEquals(UPDATED_FUNCTION_NAME, function.getName());
    }

    @Test
    public void canChangeDescription() {
        Function function = prepareTestFunction();
        functionService.saveFunction(function);
        LOGGER.debug(FUNCTION_TEMPLATE,
                function.getId(),
                function.getName(),
                function.getDescription(),
                function.getPrefix(),
                function.getScaleInAbility(),
                function.getScaleOutAbility());
        function.setDescription(UPDATED_FUNCTION_DESC);
        functionService.saveFunction(function);
        LOGGER.debug(FUNCTION_TEMPLATE,
                function.getId(),
                function.getName(),
                function.getDescription(),
                function.getPrefix(),
                function.getScaleInAbility(),
                function.getScaleOutAbility());
        Assert.assertEquals(UPDATED_FUNCTION_DESC, function.getDescription());
    }

    @Test
    public void canChangePrefix() {
        Function function = prepareTestFunction();
        functionService.saveFunction(function);
        LOGGER.debug(FUNCTION_TEMPLATE,
                function.getId(),
                function.getName(),
                function.getDescription(),
                function.getPrefix(),
                function.getScaleInAbility(),
                function.getScaleOutAbility());
        function.setPrefix(UPDATED_FUNCTION_PREFIX);
        functionService.saveFunction(function);
        LOGGER.debug(FUNCTION_TEMPLATE,
                function.getId(),
                function.getName(),
                function.getDescription(),
                function.getPrefix(),
                function.getScaleInAbility(),
                function.getScaleOutAbility());
        Assert.assertEquals(UPDATED_FUNCTION_PREFIX, function.getPrefix());
    }

    @Test
    public void canChangeScaleInAbility() {
        Function function = prepareTestFunction();
        functionService.saveFunction(function);
        LOGGER.debug(FUNCTION_TEMPLATE,
                function.getId(),
                function.getName(),
                function.getDescription(),
                function.getPrefix(),
                function.getScaleInAbility(),
                function.getScaleOutAbility());
        function.setScaleInAbility(UPDATED_FUNCTION_SCALE_IN);
        functionService.saveFunction(function);
        LOGGER.debug(FUNCTION_TEMPLATE,
                function.getId(),
                function.getName(),
                function.getDescription(),
                function.getPrefix(),
                function.getScaleInAbility(),
                function.getScaleOutAbility());
        Assert.assertEquals(UPDATED_FUNCTION_SCALE_IN, function.getScaleInAbility());
    }

    @Test
    public void canChangeScaleOutAbility() {
        Function function = prepareTestFunction();
        functionService.saveFunction(function);
        LOGGER.debug(FUNCTION_TEMPLATE,
                function.getId(),
                function.getName(),
                function.getDescription(),
                function.getPrefix(),
                function.getScaleInAbility(),
                function.getScaleOutAbility());
        function.setScaleOutAbility(UPDATED_FUNCTION_SCALE_OUT);
        functionService.saveFunction(function);
        LOGGER.debug(FUNCTION_TEMPLATE,
                function.getId(),
                function.getName(),
                function.getDescription(),
                function.getPrefix(),
                function.getScaleInAbility(),
                function.getScaleOutAbility());
        Assert.assertEquals(UPDATED_FUNCTION_SCALE_OUT, function.getScaleOutAbility());
    }

    @Test
    public void canDeleteFunction() {
        Function function = prepareTestFunction();
        functionService.saveFunction(function);
        LOGGER.debug(FUNCTION_TEMPLATE,
                function.getId(),
                function.getName(),
                function.getDescription(),
                function.getPrefix(),
                function.getScaleInAbility(),
                function.getScaleOutAbility());
        Long id = function.getId();
        if (functionService.getFunctionById(id).isPresent()) {
            functionService.deleteFunction(functionService.getFunctionById(id).get());
        }

        Assert.assertEquals(0, functionService.getAll().size());
    }

}
