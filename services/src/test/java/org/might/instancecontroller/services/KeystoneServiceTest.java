package org.might.instancecontroller.services;

import com.sun.jersey.api.client.ClientResponse;
import org.might.instancecontroller.services.transport.impl.RestResponse;
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
        RestResponse response = keystoneService.authenticate();
        when(response.getStatus()).thenReturn(201);
        Assert.assertEquals(201, response.getStatus());
    }

    @Test
    public void canConnectToKeystoneService() {
        when(keystoneService.authenticate()).thenReturn(mock(RestResponse.class));
        when(keystoneService.isConnected()).thenReturn(true);
        keystoneService.authenticate();
        Assert.assertEquals(true, keystoneService.isConnected());
    }

}
