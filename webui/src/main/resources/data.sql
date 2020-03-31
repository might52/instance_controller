insert into IMAGE (ID, REFERENCE) values (1, 'testImageReference' );
insert into FLAVOR (ID, REFERENCE) values (1, 'testFlavorReference' );
insert into CONFIGURATION (ID, STRIPT) values (1, 'testScriptConfiguration' );
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
                                'testFunction',
                                'testDescFunction',
                                'testPrefixFunction',
                                1,
                                1,
                                1,
                                FALSE,
                                FALSE
                               );

insert into SERVER (ID, NAME, SERVER_ID, FUNCTION_ID) values (
                                                              1,
                                                              'testPrefixFunction:testServer1',
                                                              'testServerIdFrom openstack',
                                                              1
                                                             );
insert into SERVER (ID, NAME, SERVER_ID, FUNCTION_ID) values (
                                                                 2,
                                                                 'testPrefixFunction:testServer2',
                                                                 'testServerIdFrom openstack2',
                                                                 1
                                                             );

SELECT * from IMAGE;
SELECT * from FLAVOR;
SELECT * from CONFIGURATION;
SELECT * from FUNCTION;
select * from SERVER;
