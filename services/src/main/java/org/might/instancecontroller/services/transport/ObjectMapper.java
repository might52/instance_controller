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

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.DataOutput;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;

public class ObjectMapper extends com.fasterxml.jackson.databind.ObjectMapper {
    private static final long serialVersionUID = 1837704215651776085L;

    private void autoconfigureFeatures(Object value) {
        JavaType javaType = _typeFactory.constructType(value.getClass());
        autoconfigureFeatures(javaType);
    }

    private void autoconfigureFeatures(JavaType javaType) {
        Annotation rootAnnotation = javaType.getRawClass().getAnnotation(JsonRootName.class);
        this.configure(SerializationFeature.WRAP_ROOT_VALUE, rootAnnotation != null);
    }

    @Override
    public String writeValueAsString(Object value) throws JsonProcessingException {
        autoconfigureFeatures(value);
        return super.writeValueAsString(value);
    }

    @Override
    public void writeValue(DataOutput out, Object value) throws IOException {
        autoconfigureFeatures(value);
        super.writeValue(out, value);
    }

    @Override
    public void writeValue(Writer w, Object value) throws IOException {
        autoconfigureFeatures(value);
        super.writeValue(w, value);
    }

    @Override
    public byte[] writeValueAsBytes(Object value) throws JsonProcessingException {
        autoconfigureFeatures(value);
        return super.writeValueAsBytes(value);
    }

}