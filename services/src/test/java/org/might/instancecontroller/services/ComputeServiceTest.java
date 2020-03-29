package org.might.instancecontroller.services;

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
    public void canGetNotEmpSyServerList() {
        when(computeService.getListServer()).thenReturn(new String());
        Object obj = computeService.getListServer();
        LOGGER.info(String.format("received object: %s", obj));
        Assert.assertNotNull(obj);
    }

    @Test
    public void canGetServerStatusById() {
        when(computeService.getServerStatus(INSTANCE_ID)).thenReturn(ServerStatus.ACTIVE.getValue());
        String status = computeService.getServerStatus(INSTANCE_ID);
        Assert.assertEquals(ServerStatus.ACTIVE.getValue(), status);
    }

    @Test
    public void canGetServerNameById() {
        when(computeService.getServerName(INSTANCE_ID)).thenReturn("first_vm_node");
        String instanceName = computeService.getServerName(INSTANCE_ID);
        Assert.assertEquals("first_vm_node", instanceName);
    }


}
