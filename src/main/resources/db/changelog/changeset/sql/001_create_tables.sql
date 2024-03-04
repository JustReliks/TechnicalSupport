--liquibase formatted sql
--changeset Rakitin Danila:create tables
create TABLE t_user (
    id serial NOT NULL constraint t_user_pk PRIMARY KEY,
    username varchar(200) NOT NULL,
    password varchar(200) NOT NULL
);

create TABLE t_role (
    id serial NOT NULL constraint t_role_pk PRIMARY KEY,
    name varchar(200) NOT NULL
);

insert into t_role(id, name) values (0, 'USER'), (1, 'OPERATOR'), (2, 'ADMIN');

create TABLE t_user_role(
    id serial NOT NULL constraint t_user_role_pk PRIMARY KEY,
    user_id integer NOT NULL REFERENCES t_user(id) ON delete CASCADE ON update CASCADE,
    role_id integer NOT NULL REFERENCES t_role(id) ON delete CASCADE ON update CASCADE
);

create TABLE t_status(
    id serial NOT NULL constraint t_status_pk PRIMARY KEY,
    name varchar(50) NOT NULL
);

insert into t_status(id, name) values (0, 'DRAFT'), (1, 'SENT'), (2, 'ACCEPTED'), (3, 'REJECTED');

create TABLE t_request(
    id serial NOT NULL constraint t_request_pk PRIMARY KEY,
    name varchar(50) NOT NULL,
    phone_number varchar(10) NOT NULL,
    message text NOT NULL,
    created_by integer NOT NULL REFERENCES t_user(id),
    status_id integer NOT NULL REFERENCES t_status(id),
    create_at timestamp NOT NULL
);