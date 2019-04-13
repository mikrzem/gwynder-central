CREATE TABLE IF NOT EXISTS central_user (
    id            bigserial    NOT NULL
        CONSTRAINT central_user_pkey
            PRIMARY KEY,
    email         varchar(255) NOT NULL
        CONSTRAINT uk_c66wquerj7r5owhu67dn3er9c
            UNIQUE,
    password_hash varchar(255) NOT NULL
);

DROP TABLE IF EXISTS health_log;
DROP TABLE IF EXISTS proxy_health;
DROP TABLE IF EXISTS proxy_dashboard;
DROP TABLE IF EXISTS proxy_application;
DROP TABLE IF EXISTS proxy_api;
DROP TABLE IF EXISTS dashboard_log_row;
DROP TABLE IF EXISTS dashboard_log;

CREATE TABLE IF NOT EXISTS security_options (
    id                bigserial NOT NULL
        CONSTRAINT security_options_pkey
            PRIMARY KEY,
    show_registration boolean   NOT NULL,
    admin_id          bigint
        CONSTRAINT fk3pi7hoee3xjlm5qil2diyh5nc
            REFERENCES central_user
);

CREATE TABLE dashboard_log (
    id           bigserial    NOT NULL
        CONSTRAINT dashboard_log_pkey
            PRIMARY KEY,
    generated_at timestamp    NOT NULL,
    owner        varchar(255) NOT NULL,
    title        varchar(255) NOT NULL
);

CREATE TABLE dashboard_log_row (
    id          bigserial    NOT NULL
        CONSTRAINT dashboard_log_row_pkey
            PRIMARY KEY,
    content     varchar(255),
    description varchar(255) NOT NULL,
    log_id      bigint       NOT NULL
        CONSTRAINT fki5dbwwf8eafaul0wub6mhnye8
            REFERENCES dashboard_log
);

CREATE TABLE proxy_api (
    id   bigserial    NOT NULL
        CONSTRAINT proxy_api_pkey
            PRIMARY KEY,
    name varchar(255) NOT NULL
        CONSTRAINT uk_3epklfbyr6pc9xqdpkkb2c7m0
            UNIQUE,
    path varchar(255) NOT NULL
);

CREATE TABLE proxy_application (
    id           bigserial    NOT NULL
        CONSTRAINT proxy_application_pkey
            PRIMARY KEY,
    active       boolean      NOT NULL,
    display_name varchar(255) NOT NULL,
    path         varchar(255) NOT NULL,
    start_path   varchar(255) NOT NULL,
    api_id       bigint       NOT NULL
        CONSTRAINT uk_eo79gsnjkdkxd48gugbouhteb
            UNIQUE
        CONSTRAINT fkpek4hggl2tuxa75go05of6tn4
            REFERENCES proxy_api
);

CREATE TABLE proxy_dashboard (
    id     bigserial NOT NULL
        CONSTRAINT proxy_dashboard_pkey
            PRIMARY KEY,
    active boolean   NOT NULL,
    path   varchar(255),
    api_id bigint    NOT NULL
        CONSTRAINT uk_3ygf98vup1qm5usy3hf6fj2m9
            UNIQUE
        CONSTRAINT fkhtoaa9trxc3thkwp41gv9lglj
            REFERENCES proxy_api
);

CREATE TABLE proxy_health (
    id     bigserial NOT NULL
        CONSTRAINT proxy_health_pkey
            PRIMARY KEY,
    active boolean   NOT NULL,
    path   varchar(255),
    api_id bigint    NOT NULL
        CONSTRAINT uk_9hduy45d9e7pt6s54c52018iw
            UNIQUE
        CONSTRAINT fkby3gsex73hyq3rrr53drbl85u
            REFERENCES proxy_api
);

CREATE TABLE health_log (
    id            bigserial    NOT NULL
        CONSTRAINT health_log_pkey
            PRIMARY KEY,
    api           varchar(255) NOT NULL,
    healthy       boolean      NOT NULL,
    response_code integer      NOT NULL,
    startup_time  varchar(255) NOT NULL
);
