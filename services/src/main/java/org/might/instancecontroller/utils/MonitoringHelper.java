package org.might.instancecontroller.utils;

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

    @Autowired
    public MonitoringHelper(SettingsHelper settingsHelper) {
        MonitoringHelper.SETTING_HELPER = settingsHelper;
    }
}
