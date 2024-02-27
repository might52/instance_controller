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

import org.might.instancecontroller.dba.entity.Function;

public interface InstanceService {
     /**
      * Perform instantiation new instance of function.
      * @param function {@link Function}.
      * @throws InterruptedException
      */
     void instantiate(Function function) throws InterruptedException;

     /**
      * Perform releasing selected instance of function.
      * @param serverId instance id of server.
      * @throws InterruptedException
      */
     void releaseInstanceByServerId(Long serverId) throws InterruptedException;

     /**
      * Perform the releasing and new instantiation based on old instance of function.
      * @param serverId instance id of server.
      * @throws InterruptedException
      */
     void reInstantiateByServerId(Long serverId) throws InterruptedException;
}
