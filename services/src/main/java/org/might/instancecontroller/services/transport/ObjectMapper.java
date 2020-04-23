package org.might.instancecontroller.services.transport;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

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
    public void writeValue(Writer w, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        autoconfigureFeatures(value);
        super.writeValue(w, value);
    }

    @Override
    public byte[] writeValueAsBytes(Object value) throws JsonProcessingException {
        autoconfigureFeatures(value);
        return super.writeValueAsBytes(value);
    }

}