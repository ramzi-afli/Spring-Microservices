
/* Data   initialization   */

INSERT IGNORE INTO oauth_client_details (client_id, client_secret, web_server_redirect_uri, scope, access_token_validity, refresh_token_validity, resource_ids, authorized_grant_types, additional_information) VALUES ('microservices_app', '{bcrypt}$2y$10$9RcYsGUiHy2kopNLoQJscO8IoCSLB2cKNrh6GlW1oru3QDPrci1Fu', 'http://localhost:9091/code', 'READ,WRITE', '3600', '172800', 'cv-server', 'authorization_code,password,refresh_token,implicit', '{}');

INSERT IGNORE INTO permission (NAME) VALUES
                                  ('create'), /* 1 */
                                  ('read'),  /*  2 */
                                  ('update'), /* 3 */
                                  ('delete') , /* 4 */
                                  ('delete_user'), /* 5 */
                                  ('fetch_users')  ; /* 6 */

INSERT IGNORE INTO role ( ID, NAME) VALUES
                            (1,'ROLE_user'),(2,'ROLE_admin'),(3,'ROLE_client');

INSERT IGNORE INTO permission_role (PERMISSION_ID, ROLE_ID) VALUES
                                                         (1,1), /*create-> user */
                                                         (2,1), /* read user ->user */
                                                         (3,1), /* update user -> user */
                                                         (4,1), /* delete  ->user */
                                                         (1,2), /*create-> admin */

                                                         (2,2),  /* read user ->admin  */
                                                         (3,2),  /* update user ->admin */
                                                         (5,2), /* delete user ->admin */
                                                         (6,2), /*  fetch users -> admin */

                                                         (1,3), /*create-> client */
                                                         (2,3), /*read-> client */
                                                         (3,3), /*update-> client */
                                                         (4,3), /* delete  user -> client */
                                                         (6,3);  /* fetch users -> client */


insert IGNORE into user (id, username,password, email, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked) VALUES ('1', 'ramzi','{bcrypt}$2y$10$huP4jiJ0LRKlyKs9y0yTIujQqL4yXRa/DfbLTiu12fRpURjj1oXOq', 'ramzi.barhoumi@microservices.com', '1', '1', '1', '1');
insert IGNORE into  user (id, username,password, email, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked) VALUES ('2', 'admin', '{bcrypt}$2y$10$huP4jiJ0LRKlyKs9y0yTIujQqL4yXRa/DfbLTiu12fRpURjj1oXOq','admin@microservices.com', '1', '1', '1', '1');
insert IGNORE into  user (id, username,password, email, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked) VALUES ('3', 'client', '{bcrypt}$2y$10$EH1HNslnxNygGQjSu06OxOc3y0dKOAZHAUMr0aBLi1OWoqs561Cim','client@microservices.com', '1', '1', '1', '1');

INSERT IGNORE INTO role_user (ROLE_ID, USER_ID)
VALUES
    (1, 1) /* user */,
    (2, 2) /* admin */ ,
    (3,3)  /* client*/  ;


