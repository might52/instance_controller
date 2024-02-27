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
package org.might.instancecontroller.services.transport.impl;

import com.sun.jersey.api.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;


public class RestResponse implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestResponse.class);

    private final ClientResponse clientResponse;
    private String stringEntity;

    public RestResponse(ClientResponse clientResponse) {
        this(clientResponse, null);
    }

    public RestResponse(ClientResponse clientResponse, String encoding) {
        this.clientResponse = clientResponse;
        this.stringEntity = null;
        try {
            InputStream inputStream = clientResponse.getEntityInputStream();
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            if (encoding == null || encoding.isEmpty()) {
                this.stringEntity = result.toString();
            } else {
                this.stringEntity = result.toString(encoding);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while reading the response from service", e);
        } finally {
            clientResponse.close();
        }
    }

    public String getStringEntity() {
        return stringEntity;
    }

    public ClientResponse getClientResponse() {
        return clientResponse;
    }

    public int getStatus() {
        return getClientResponse().getStatus();
    }

    public List<NewCookie> getCookies() {
        return getClientResponse().getCookies();
    }

    public MultivaluedMap<String, String> getHeaders() {
        return getClientResponse().getHeaders();
    }

    public String toString() {
        return getClientResponse().toString();
    }


}
