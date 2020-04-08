insert into IMAGE (ID, REFERENCE) values (1, '1884fe82-552b-4053-a3f6-111f969407b9' );
insert into FLAVOR (ID, REFERENCE) values (1, '0b239a96-de37-4123-8839-62d7e585098f' );
insert into CONFIGURATION (ID, SCRIPT) values (1, '#!/bin/bash
cat /etc/zabbix/zabbix_agentd.conf | sed -r "s;Hostname=(.)*;Hostname=test;g" > /etc/zabbix/zabbix_agentd.conf_b
mv /etc/zabbix/zabbix_agentd.conf_b /etc/zabbix/zabbix_agentd.conf -f
systemctl restart zabbix-agent');
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

-- insert into SERVER (ID, NAME, SERVER_ID, FUNCTION_ID) values (
--                                                               1,
--                                                               'webserv:webserv1',
--                                                               'webserv:webserv1fromOpenstack',
--                                                               1
--                                                              );
-- insert into SERVER (ID, NAME, SERVER_ID, FUNCTION_ID) values (
--                                                                  2,
--                                                                  'webserv:webserv2',
--                                                                  'webserv:webserv2fromOpenstack',
--                                                                  1
--                                                              );

-- SELECT * from IMAGE;
-- SELECT * from FLAVOR;
-- SELECT * from CONFIGURATION;
-- SELECT * from FUNCTION;
-- select * from SERVER;
