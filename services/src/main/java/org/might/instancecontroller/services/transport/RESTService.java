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
package org.might.instancecontroller.services.transport;

import com.fasterxml.jackson.core.type.TypeReference;
import org.might.instancecontroller.services.transport.impl.RestResponse;

import javax.ws.rs.core.MultivaluedMap;

public interface RESTService {

    /**
     * Perform GET request to the endpoint.
     * @param endpointUrl url endpoint.
     * @param headers headers map.
     * @param typeReference type of required object.
     * @param <T>
     * @return
     */
    <T> T get(String endpointUrl,
              MultivaluedMap<String, String> headers,
              TypeReference<T> typeReference);

    /**
     * Perform GET request to the endpoint.
     * @param endpointUrl url endpoint.
     * @param headers headers map.
     * @param typeReference type of required object.
     * @param <T>
     * @return Return RestResponse object.
     */
    <T> RestResponse getRaw(String endpointUrl,
                            MultivaluedMap<String, String> headers,
                            TypeReference<T> typeReference);

    /**
     * Perform POST request to the endpoint.
     * @param endpointUrl url endpoint.
     * @param data class body of request.
     * @param headers headers map.
     * @param typeReference type of required object.
     * @param <T>
     * @return Return required object.
     */
    <T> T post(String endpointUrl,
               Object data,
               MultivaluedMap<String, String> headers,
               TypeReference<T> typeReference);

    /**
     * Perform POST request to the endpoint.
     * @param endpointUrl url endpoint.
     * @param data class body of request.
     * @param headers headers map.
     * @param typeReference type of required object.
     * @param <T>
     * @return Return RestResponse object.
     */
    <T> RestResponse postRaw(String endpointUrl,
               Object data,
               MultivaluedMap<String, String> headers,
               TypeReference<T> typeReference);

    /**
     * Perform UPDATE request to the endpoint.
     * @param endpointUrl url endpoint.
     * @param data class body of request.
     * @param <T>
     * @return
     */
    <T> T update(String endpointUrl, Object data);

    /**
     * Perform DELETE request to the endpoint.
     * @param endpointUrl url endpoint.
     * @param data class body of request.
     * @param headers headers map.
     * @param typeReference type of required object.
     * @param <T>
     * @return Return required object.
     */
    <T> T delete(String endpointUrl,
                 Object data,
                 MultivaluedMap<String, String> headers,
                 TypeReference<T> typeReference);

    /**
     * Perform DELETE request to the endpoint.
     * @param endpointUrl url endpoint.
     * @param data class body of request.
     * @param headers headers map.
     * @param typeReference type of required object.
     * @param <T>
     * @return Return RestResponse object.
     */
    <T> RestResponse deleteRaw(String endpointUrl,
                 Object data,
                 MultivaluedMap<String, String> headers,
                 TypeReference<T> typeReference);

}
