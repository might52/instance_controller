package com.might.instancecontroller.services.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.might.instancecontroller.models.servers.Instance;
import com.might.instancecontroller.services.InstanceStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class DeserializationTest {

    private String serverResponce;
    private ObjectMapper jsonSerializer;

    public DeserializationTest() throws IOException {
        this.serverResponce = Files.readString(Paths.get("src/main/resources/Jsons/Server.json"), StandardCharsets.UTF_8);
        this.jsonSerializer = new ObjectMapper();
        this.jsonSerializer.enable(SerializationFeature.WRAP_ROOT_VALUE);
    }

    @Test
    public void canGetStatusAfterServiceDeserialization() throws IOException {
        Instance instance = this.jsonSerializer.readValue(this.serverResponce, new TypeReference<Instance>() {});
        String status = instance.getServer().getStatus();
        Assert.assertEquals(InstanceStatus.ACTIVE, InstanceStatus.getInstanceStatus(status));
    }

}
