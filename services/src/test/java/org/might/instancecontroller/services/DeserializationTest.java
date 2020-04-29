package org.might.instancecontroller.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.Test;
import org.might.instancecontroller.models.monitoring.HostResponse;
import org.might.instancecontroller.models.monitoring.HostResult;
import org.might.instancecontroller.models.monitoring.NotificationModel;
import org.might.instancecontroller.models.servers.*;
import org.might.instancecontroller.services.transport.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeserializationTest {

    private final String serversReponse;
    private final String serverResponse;
    private final String creationResponse;
    private final String deletionResponse;
    private final String eventRaised;
    private final ObjectMapper jsonSerializer;

    private final static String STATUS_EVENT = "PROBLEM";
    private static final String NAME_EVENT = "Unavailable by ICMP ping";
    private static final Time TIME_EVENT = Time.valueOf("20:50:30");
    private static final String SUBJECT_EVENT = "problem|Unavailable by ICMP ping|192.168.20.107|PROBLEM|High";
    private static final String HOST_IP_EVENT = "192.168.20.107";
    private static final Date DATE_EVENT = Date.valueOf("2020.04.28".replace(".", "-"));
    private static final String HOST_NAME_EVENT = "webServerFunction_webserv_1";
    private static final String ACK_STATUS_EVENT = "No";
    private static final String PROBLEM_ID_EVENT = "569";
    private static final boolean ACTIVE_EVENT = true;
    private static final String HOST_DESC_EVENT = "c5d8e3dd-5ffa-4ffa-b15c-7b9683ff14e1";
    private static final String SEVERITY_EVENT = "High";


    private final static String HOST_ID = "10275";

    public DeserializationTest() throws IOException {
        this.serverResponse = Files.readString(Paths.get("src/main/resources/Jsons/Server.json"), StandardCharsets.UTF_8);
        this.serversReponse = Files.readString(Paths.get("src/main/resources/Jsons/Servers.json"), StandardCharsets.UTF_8);
        this.creationResponse = Files.readString(Paths.get("src/main/resources/Jsons/MonitoringCreationResult.json"), StandardCharsets.UTF_8);
        this.deletionResponse = Files.readString(Paths.get("src/main/resources/Jsons/MonitoringDeletionResult.json"), StandardCharsets.UTF_8);
        this.eventRaised = Files.readString(Paths.get("src/main/resources/Jsons/EventRaised.json"), StandardCharsets.UTF_8);
        this.jsonSerializer = new ObjectMapper();
        this.jsonSerializer.enable(SerializationFeature.WRAP_ROOT_VALUE);
        this.jsonSerializer.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

//        this.jsonSerializer.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
//        this.jsonSerializer.enable(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
//        this.jsonSerializer.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Test
    public void canGetServerStatus() throws IOException {
        Instance instance = this.jsonSerializer.readValue(this.serverResponse, new TypeReference<Instance>() {});
        String status = instance.getOpenstackServer().getStatus();
        Assert.assertEquals(ServerStatus.ACTIVE, ServerStatus.getServerStatus(status));
    }

    @Test
    public void canGetServerName() throws IOException {
        Instance instance = this.jsonSerializer.readValue(this.serverResponse, new TypeReference<Instance>() {});
        String name = instance.getOpenstackServer().getName();
        Assert.assertEquals("first_vm_node", name);
    }

    @Test
    public void canGetServerAddresses() throws IOException {
        Instance instance = this.jsonSerializer.readValue(this.serverResponse, new TypeReference<Instance>() {});
        Assert.assertEquals(getEtalonInstance().getOpenstackServer().getAddresses(), instance.getOpenstackServer().getAddresses());
    }

    @Test
    public void canGetServerList() throws IOException {
        Servers instances =
                this.jsonSerializer.readValue(
                        this.serversReponse,
                        new TypeReference<Servers>() {});
        Assert.assertNotNull(instances);
    }

    @Test
    public void canGetMonitoringCreationResult() throws IOException {
        HostResponse hostResponse =
                this.jsonSerializer.readValue(
                        this.creationResponse,
                        new TypeReference<HostResponse>() {
                        }
                );
        Assert.assertEquals(HOST_ID, hostResponse.getResult().getHostids().get(0));
    }

    @Test
    public void canGetMonitoringDeletionResult() throws IOException {
        HostResponse hostResponse =
                this.jsonSerializer.readValue(
                        this.deletionResponse,
                        new TypeReference<HostResponse>() {
                        }
                );
        Assert.assertEquals(HOST_ID, hostResponse.getResult().getHostids().get(0));
    }

    @Test
    public void canGetEventStatus() throws IOException {
        NotificationModel notificationModel =
                this.jsonSerializer.readValue(
                        this.eventRaised,
                        new TypeReference<NotificationModel>() {
                        }
                );
        Assert.assertEquals(STATUS_EVENT, notificationModel.getStatus());
    }


    @Test
    public void canGetEventName() throws IOException {
        NotificationModel notificationModel =
                this.jsonSerializer.readValue(
                        this.eventRaised,
                        new TypeReference<NotificationModel>() {
                        }
                );
        Assert.assertEquals(NAME_EVENT, notificationModel.getName());
    }

    @Test
    public void canGetEventTime() throws IOException {
        NotificationModel notificationModel =
                this.jsonSerializer.readValue(
                        this.eventRaised,
                        new TypeReference<NotificationModel>() {
                        }
                );
        Assert.assertEquals(TIME_EVENT, notificationModel.getTime());
    }

    @Test
    public void canGetEventSubjectEvent() throws IOException {
        NotificationModel notificationModel =
                this.jsonSerializer.readValue(
                        this.eventRaised,
                        new TypeReference<NotificationModel>() {
                        }
                );
        Assert.assertEquals(SUBJECT_EVENT, notificationModel.getSubject());
    }


    @Test
    public void canGetEventDate() throws IOException {
        NotificationModel notificationModel =
                this.jsonSerializer.readValue(
                        this.eventRaised,
                        new TypeReference<NotificationModel>() {
                        }
                );
        Assert.assertEquals(DATE_EVENT, notificationModel.getDate());
    }

    @Test
    public void canGetEventHostIp() throws IOException {
        NotificationModel notificationModel =
                this.jsonSerializer.readValue(
                        this.eventRaised,
                        new TypeReference<NotificationModel>() {
                        }
                );
        Assert.assertEquals(HOST_IP_EVENT, notificationModel.getHostIp());
    }

    @Test
    public void canGetEventHostName() throws IOException {
        NotificationModel notificationModel =
                this.jsonSerializer.readValue(
                        this.eventRaised,
                        new TypeReference<NotificationModel>() {
                        }
                );
        Assert.assertEquals(HOST_NAME_EVENT, notificationModel.getHostName());
    }

    @Test
    public void canGetEventAckStatus() throws IOException {
        NotificationModel notificationModel =
                this.jsonSerializer.readValue(
                        this.eventRaised,
                        new TypeReference<NotificationModel>() {
                        }
                );
        Assert.assertEquals(ACK_STATUS_EVENT, notificationModel.getAckStatus());
    }

    @Test
    public void canGetEventProblemId() throws IOException {
        NotificationModel notificationModel =
                this.jsonSerializer.readValue(
                        this.eventRaised,
                        new TypeReference<NotificationModel>() {
                        }
                );
        Assert.assertEquals(PROBLEM_ID_EVENT, notificationModel.getProblemId());
    }

    @Test
    public void canGetEventActive() throws IOException {
        NotificationModel notificationModel =
                this.jsonSerializer.readValue(
                        this.eventRaised,
                        new TypeReference<NotificationModel>() {
                        }
                );
        Assert.assertEquals(ACTIVE_EVENT, notificationModel.getActive());
    }

    @Test
    public void canGetEventHostDesc() throws IOException {
        NotificationModel notificationModel =
                this.jsonSerializer.readValue(
                        this.eventRaised,
                        new TypeReference<NotificationModel>() {
                        }
                );
        Assert.assertEquals(HOST_DESC_EVENT, notificationModel.getHostDesc());
    }

    @Test
    public void canGetEventSeverity() throws IOException {
        NotificationModel notificationModel =
                this.jsonSerializer.readValue(
                        this.eventRaised,
                        new TypeReference<NotificationModel>() {
                        }
                );
        Assert.assertEquals(SEVERITY_EVENT, notificationModel.getSeverity());
    }

    /**
     * Getting the etalon instance.
     * @return Instance.class
     */
    private Instance getEtalonInstance() {
        Instance instance = new Instance();
        OpenstackServer openstackServer = new OpenstackServer();
        Addresses addresses = new Addresses();
        Map<String, List<Network>> networks = new HashMap<>();
        List<Network> networkList = new ArrayList<>() {{
            add(new Network(){{
                setMacAddr("fa:16:3e:6d:58:59");
                setVersion(4);
                setAddr("192.168.30.26");
                setType("fixed");
            }});
        }};
        networks.put("internal_network", networkList);
        networkList = new ArrayList<>() {{
            add(new Network(){{
                setMacAddr("fa:16:3e:84:f6:4f");
                setVersion(4);
                setAddr("192.168.20.102");
                setType("fixed");
            }});
        }};
        networks.put("external_network", networkList);
        addresses.setNetworks(networks);
        openstackServer.setAddresses(addresses);
        instance.setOpenstackServer(openstackServer);
        return instance;
    }
}
