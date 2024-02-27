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
package org.might.instancecontroller.utils;

import org.might.instancecontroller.dba.entity.Event;
import org.might.instancecontroller.dba.entity.Server;
import org.might.instancecontroller.models.monitoring.HostCreateModel;
import org.might.instancecontroller.models.monitoring.HostDeletionModel;
import org.might.instancecontroller.models.monitoring.HostGroupModel;
import org.might.instancecontroller.models.monitoring.HostInterfaceModel;
import org.might.instancecontroller.models.monitoring.HostParamsModel;
import org.might.instancecontroller.models.monitoring.HostTemplateModel;
import org.might.instancecontroller.models.monitoring.NotificationModel;
import org.might.instancecontroller.models.servers.OpenstackServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;

@Service
public class MonitoringHelper implements Serializable {


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
     *
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
                        add(new HostGroupModel() {{
                            setGroupId(FIRST_GROUP);
                        }});
                        add(new HostGroupModel() {{
                            setGroupId(SECOND_GROUP);
                        }});
                    }});
                    setTemplates(new ArrayList<HostTemplateModel>() {{
                        add(new HostTemplateModel() {{
                            setTemplateid(FIRST_TEMPLATE);
                        }});
                        add(new HostTemplateModel() {{
                            setTemplateid(SECOND_TEMPLATE);
                        }});
                    }});
                    setInterfaces(new ArrayList<HostInterfaceModel>() {{
                        add(new HostInterfaceModel() {{
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
     *
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
     *
     * @param notificationModel
     * @return {@link Event}
     */
    public static Event getEventByNotification(NotificationModel notificationModel) {

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
     *
     * @param dbEvent  {@link Event} from DB.
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
