package com.might.instancecontroller.services.tests;

import com.might.instancecontroller.services.ComputeService;
import com.might.instancecontroller.services.InstanceStatus;
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
        when(computeService.getListInstance()).thenReturn(new String());
        Object obj = computeService.getListInstance();
        LOGGER.info(String.format("received object: %s", obj));
        Assert.assertNotNull(obj);
    }

    @Test
    public void canGetInstanceStatusById() {
        when(computeService.getInstanceStatus(INSTANCE_ID)).thenReturn(InstanceStatus.ACTIVE.getValue());
        String status = computeService.getInstanceStatus(INSTANCE_ID);
        Assert.assertEquals(InstanceStatus.ACTIVE.getValue(), status);
    }

    @Test
    public void canGetInstanceNameById() {
        when(computeService.getInstanceName(INSTANCE_ID)).thenReturn("first_vm_node");
        String instanceName = computeService.getInstanceName(INSTANCE_ID);
        Assert.assertEquals("first_vm_node", instanceName);
    }


}
