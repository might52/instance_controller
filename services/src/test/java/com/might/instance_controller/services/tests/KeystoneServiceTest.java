package com.might.instance_controller.services.tests;

import com.might.instance_controller.services.KeystoneService;
import com.might.instance_controller.services.transport.impl.RestResponse;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.ws.Response;

@SpringBootTest(classes = KeystoneServiceTest.class)
@ComponentScan({"com.might.instance_controller.*"})
@RunWith(SpringJUnit4ClassRunner.class)
public class KeystoneServiceTest {

    @Autowired
    private KeystoneService keystoneService;

    @Test
    public void AuthenticateSuccessfullTest() {
        RestResponse response = (RestResponse) keystoneService.authenticate();
        Assert.assertEquals(201, response.getStatus());
    }

    @Test
    public void CheckConnectionStatusTrue() {
        keystoneService.authenticate();
        Assert.assertEquals(true, keystoneService.isConnected());
    }

}
