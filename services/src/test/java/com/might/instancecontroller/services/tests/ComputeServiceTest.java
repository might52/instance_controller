package com.might.instancecontroller.services.tests;


import com.might.instancecontroller.services.ComputeService;
import com.might.instancecontroller.services.KeystoneService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@SpringBootTest(classes = KeystoneServiceTest.class)
@ComponentScan({"com.might.instancecontroller.*"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ComputeServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputeServiceTest.class);

    @Autowired
    private ComputeService computeService;

    @Autowired
    private KeystoneService keystoneService;


    @Before
    public void prepareTest(){
        keystoneService.authenticate();
    }

    @Test
    public void getListInstanceTestNotEmptyList(){
        Object obj = computeService.getListInstance();
        LOGGER.info(String.format("received object: %s", obj));
        Assert.assertNotNull(obj);
    }

}
