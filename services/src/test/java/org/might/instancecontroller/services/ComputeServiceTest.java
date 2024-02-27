/*
 * MIT License
 *
 * Copyright (c) 2024 Andrei F._
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
