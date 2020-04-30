package org.might.instancecontroller.utils;

import org.might.instancecontroller.dba.entity.Event;
import org.might.instancecontroller.dba.entity.Server;
import org.might.instancecontroller.models.monitoring.*;
import org.might.instancecontroller.models.servers.OpenstackServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;

@Service
public class MonitoringHelper implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringHelper.class);

    private static final long serialVersionUID = -5644549449534208641L;
    private static final String HOST_CREATE_METHOD = "host.create";
    private static final String HOST_DELETION_METHOD = "host.delete";
    private static final String JSON_RPC = "2.0";
    private static final int DEFAULT_ID = 1;
    private static final String FIRST_GROUP = "2";
    private static final String SECOND_GROUP = "12";
    private static final String FIRST_TEMPLATE = "10001";
    private static final String SECOND_TEMPLATE = "10186";
    private static final String VALUE_ONE = "1";
    private static final String TYPE = VALUE_ONE;
    private static final String MAIN = VALUE_ONE;
    private static final String USEIP = VALUE_ONE;
    private static final String DNS = "";
    private static final String PORT = "10050";

    private static SettingsHelper SETTING_HELPER;

    /**
     * Creation model by {@link OpenstackServer}.
     * @param openstackServer
     * @return {@link HostCreateModel}
     */
    public static HostCreateModel getHostCreateModel(final OpenstackServer openstackServer) {
        HostCreateModel hostCreateModel = new HostCreateModel();
        hostCreateModel.setId(DEFAULT_ID);
        hostCreateModel.setAuth(MonitoringHelper.SETTING_HELPER.getZabbixAuthToken());
        hostCreateModel.setJsonrpc(JSON_RPC);
        hostCreateModel.setMethod(HOST_CREATE_METHOD);
        hostCreateModel.setParamsModel(
                new HostParamsModel() {{
                    setHost(openstackServer.getName());
                    setDescription(openstackServer.getId());
                    setGroups(new ArrayList<HostGroupModel>() {{
                        add(new HostGroupModel(){{
                            setGroupId(FIRST_GROUP);
                        }});
                        add(new HostGroupModel(){{
                            setGroupId(SECOND_GROUP);
                        }});
                    }});
                    setTemplates(new ArrayList<HostTemplateModel>() {{
                        add(new HostTemplateModel(){{
                            setTemplateid(FIRST_TEMPLATE);
                        }});
                        add(new HostTemplateModel(){{
                            setTemplateid(SECOND_TEMPLATE);
                        }});
                    }});
                    setInterfaces(new ArrayList<HostInterfaceModel>(){{
                        add(new HostInterfaceModel(){{
                            setType(TYPE);
                            setMain(MAIN);
                            setUseip(USEIP);
                            setDns(DNS);
                            setIp(openstackServer
                                    .getAddresses()
                                    .getNetworks()
                                    .get(FunctionHelper.NETWORK_NAME_PUBLIC)
                                    .get(0)
                                    .getAddr()
                            );
                            setPort(PORT);
                        }});
                    }});
                }}
        );

        return hostCreateModel;
    }

    /**
     * Return {@link HostDeletionModel}.
     * @param server server from DB.
     * @return {@link HostDeletionModel}
     */
    public static HostDeletionModel getHostDeletionModel(Server server) {
        HostDeletionModel hostDeletionModel = new HostDeletionModel();
        hostDeletionModel.setId(DEFAULT_ID);
        hostDeletionModel.setAuth(MonitoringHelper.SETTING_HELPER.getZabbixAuthToken());
        hostDeletionModel.setJsonrpc(JSON_RPC);
        hostDeletionModel.setMethod(HOST_DELETION_METHOD);
        hostDeletionModel.setParams(new ArrayList<>() {{
            add(server.getMonitoringId().toString());
        }});
        return hostDeletionModel;
    }

    /**
     * Return event by {@link NotificationModel}.
     * @param notificationModel
     * @return {@link Event}
     */
    public static Event getEventByNotification(NotificationModel notificationModel) {
        /**
         *   "status": "PROBLEM",
         *   "name": "Unavailable by ICMP ping",
         *   "time": "20:50:30",
         *   "subject": "problem|Unavailable by ICMP ping|192.168.20.107|PROBLEM|High",
         *   "host_ip": "192.168.20.107",
         *   "date": "2020.04.28",
         *   "host_name": "webServerFunction_webserv_1",
         *   "ack_status": "No",
         *   "problemId": "569",
         *   "active": true,
         *   "host_desc": "c5d8e3dd-5ffa-4ffa-b15c-7b9683ff14e1",
         *   "severity": "High"
         */
        Event event = new Event();
        event.setStatus(notificationModel.getStatus());
        event.setName(notificationModel.getName());
        event.setTime(notificationModel.getTime());
        event.setHostIp(notificationModel.getHostIp());
        event.setDate(notificationModel.getDate());
        event.setHostName(notificationModel.getHostName());
        event.setAckStatus(notificationModel.getAckStatus());
        event.setProblemId(notificationModel.getProblemId());
        event.setActive(notificationModel.getActive());
        event.setServerId(notificationModel.getHostDesc());
        event.setSeverity(notificationModel.getSeverity());
        return event;
    }


    /**
     * Perform compare and update the events only equal by equals.
     * @param dbEvent {@link Event} from DB.
     * @param newEvent {@link Event} from {@link NotificationModel}.
     * @return updated {@link Event}
     */
    public static Event compareAndUpdate(Event dbEvent, Event newEvent) {
        if (!dbEvent.equals(newEvent)) {
            return dbEvent;
        }

        if (!dbEvent.getAckStatus().equals(newEvent.getAckStatus())) {
            dbEvent.setAckStatus(newEvent.getAckStatus());
        }

        if (!dbEvent.getActive() == (newEvent.getActive())) {
            dbEvent.setActive(newEvent.getActive());
            dbEvent.setRecoveryTime(newEvent.getTime());
            dbEvent.setRecoveryDate(newEvent.getDate());
        }

        if (!dbEvent.getStatus().equals(newEvent.getStatus())) {
            dbEvent.setStatus(newEvent.getStatus());
        }

        return dbEvent;
    }


    @Autowired
    public MonitoringHelper(SettingsHelper settingsHelper) {
        MonitoringHelper.SETTING_HELPER = settingsHelper;
    }

}
