package com.might.instancecontroller.services.tests;

import com.might.instancecontroller.services.ComputeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ComputeServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputeServiceTest.class);
    private static final String INSTANCE_ID = "5ff7252e-e6e8-40a1-97a1-9d8452cd7280";

    @Mock
    private ComputeService computeService;

    @Test
    public void canGetNotEmptyInstanceList() {
        when(computeService.getListInstance()).thenReturn(new Object());
        Object obj = computeService.getListInstance();
        LOGGER.info(String.format("received object: %s", obj));
        Assert.assertNotNull(obj);
    }

    @Test
    public void canGetInstanceStatus() {
        String status = computeService.getInstanceStatus(INSTANCE_ID);
        Assert.assertEquals("ACTIVE", status);
    }

}