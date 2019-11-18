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

    @Mock
    private ComputeService computeService;

    @Test
    public void getListInstanceTestNotEmptyList() {
        when(computeService.getListInstance()).thenReturn(new Object());
        Object obj = computeService.getListInstance();
        LOGGER.info(String.format("received object: %s", obj));
        Assert.assertNotNull(obj);
    }

}
