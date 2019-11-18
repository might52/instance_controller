package com.might.instancecontroller.services.tests;

import com.might.instancecontroller.services.KeystoneService;
import com.might.instancecontroller.services.transport.impl.RestResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KeystoneServiceTest {

    @Mock
    private KeystoneService keystoneService;

    @Test
    public void canSuccessfullAuthenticate() {
        when(keystoneService.authenticate()).thenReturn(mock(RestResponse.class));
        RestResponse response = (RestResponse) keystoneService.authenticate();
        when(response.getStatus()).thenReturn(201);
        Assert.assertEquals(201, response.getStatus());
    }

    @Test
    public void canConnectToKeystoneService() {
        when(keystoneService.authenticate()).thenReturn(new Object());
        when(keystoneService.isConnected()).thenReturn(true);
        keystoneService.authenticate();
        Assert.assertEquals(true, keystoneService.isConnected());
    }

}
