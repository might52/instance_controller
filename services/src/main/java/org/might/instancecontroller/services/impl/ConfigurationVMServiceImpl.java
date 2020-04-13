package org.might.instancecontroller.services.impl;

import com.jcraft.jsch.*;
import org.might.instancecontroller.services.ConfigurationVMService;
import org.might.instancecontroller.utils.OSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ConfigurationVMServiceImpl implements ConfigurationVMService {

    private static OSUtils OS_UTILS;

    private static final int SSH_PORT = 22;
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int BUFFER_SIZE = 1024;

    private static String CONNECTION_STRING_TEMPLATE="{}:{}:{}";


    public boolean setUpVM(String host, String vmHostname) {
        StringBuilder commands = new StringBuilder();
        commands.append("systemctl stop zabbix-agent");
        commands.append(" && cat /etc/zabbix/zabbix_agentd.conf | sed -r 's;Hostname=(.)*;Hostname=");
        commands.append(vmHostname);
        commands.append(";g' > /etc/zabbix/zabbix_agentd.conf_b");
        commands.append(" && mv /etc/zabbix/zabbix_agentd.conf_b /etc/zabbix/zabbix_agentd.conf -f");
        commands.append(" && systemctl start zabbix-agent");
        return executeCommand(host, commands.toString());
    }

    private static boolean executeCommand(String host, String commands) {
        JSch jSch = new JSch();
        try {
            Session session = jSch.getSession(OS_UTILS.getVmUserName(), host, SSH_PORT);
            session.setPassword(OS_UTILS.getVmPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(CONNECTION_TIMEOUT);
            Channel channel = session.openChannel("exec");
            ChannelExec channelExec = (ChannelExec) channel;
            channelExec.setCommand(commands);
            channelExec.setInputStream(null);
            channelExec.setErrStream(System.err);
            channelExec.connect();
            InputStream in = channelExec.getInputStream();
            String dataFromChannel = readResponse(channelExec, in, null);
            List<String> allList = new ArrayList<>(Collections.singletonList(dataFromChannel));
            allList.forEach(System.out::println);
            channel.disconnect();
            session.disconnect();
        } catch (JSchException | IOException ex) {
            System.out.println(
                    String.format(
                            "%s \n %s",
                            ex.getMessage(),
                            ex.getLocalizedMessage()
                    )
            );
        }

        return true;
    }

    private static String readResponse(Channel channel, InputStream inputStream, String encoding) {
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            if (encoding == null || encoding.isEmpty()) {
                return result.toString();
            } else {
                return result.toString(encoding);
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Error while reading the response from server",
                    e);
        }
    }
/*
    private static String readResponse(Channel channel, InputStream in)
            throws IOException {
        StringBuilder result = new StringBuilder();
        byte[] tmp = new byte[BUFFER_SIZE];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, BUFFER_SIZE);
                if (i < 0) {
                    break;
                }
                result.append(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                int exitStatus = channel.getExitStatus();
                System.out.println("exit-status: " + exitStatus);
                break;
            }
//            trySleep(1000);
        }
        return result.toString();
    }*/

    @Autowired
    public ConfigurationVMServiceImpl(OSUtils osUtils) {
        this.OS_UTILS = osUtils;
    }
}
