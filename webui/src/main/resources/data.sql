insert into IMAGE (ID, REFERENCE) values (1, '1884fe82-552b-4053-a3f6-111f969407b9' );
insert into FLAVOR (ID, REFERENCE) values (1, '0b239a96-de37-4123-8839-62d7e585098f' );
insert into CONFIGURATION (ID, SCRIPT) values (1, 'systemctl stop zabbix-agent && cat /etc/zabbix/zabbix_agentd.conf | sed -r "s;Hostname=(.)*;Hostname=function_name;g" > /etc/zabbix/zabbix_agentd.conf_b && mv /etc/zabbix/zabbix_agentd.conf_b /etc/zabbix/zabbix_agentd.conf -f && systemctl start zabbix-agent');
INSERT INTO FUNCTION (ID,
                      NAME,
                      DESCRIPTION,
                      PREFIX,
                      CONFIGURATION_ID,
                      FLAVOR_ID,
                      IMAGE_ID,
                      SCALE_IN_ABILITY,
                      SCALE_OUT_ABILITY
                      ) values (
                                1,
                                'webServerFunction',
                                'Web server function',
                                'webserv',
                                1,
                                1,
                                1,
                                FALSE,
                                TRUE
                               );
