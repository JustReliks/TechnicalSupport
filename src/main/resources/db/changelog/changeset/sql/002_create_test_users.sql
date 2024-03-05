--liquibase formatted sql
--changeset Rakitin Danila:create mock users
INSERT INTO t_user(id, username, password) VALUES (1, 'Admin', '$2a$12$t4TX06OiI1QnVEkYr.At/Or2rNN/JC3qOFAUHGEqjU6ZHZcikKz5q'),
(2, 'Operator', '$2a$12$NmbhP/Le97c3l3bCEahgEexIfsetqTXzWx2SDJMsmqm7H8g3dW3oC'),
(3, 'User', '$2a$12$xMpWo7CpEeuLPCVOAVR1lu0czB.Jq7GF3fghw2iCXIZe4336szySO'),
(4, 'Operator_Admin','$2a$12$8MUWnr.94xhz9ABFLRZbEeWRnsEv0a/uE7Gw2nqXB2ifhNt98NQ1y');

INSERT INTO t_user_role(user_id, role_id) VALUES (1, 3), (2, 2), (3, 1), (4, 3), (3, 2);
