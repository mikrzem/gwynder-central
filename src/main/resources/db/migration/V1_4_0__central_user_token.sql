CREATE TABLE central_user_token (
    id                  bigserial NOT NULL,
    active              boolean   NOT NULL,
    application_name    varchar(255),
    authorization_token varchar(255),
    created_at          timestamp NOT NULL,
    request_code        varchar(255),
    response_code       varchar(255),
    user_id             int8      NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE central_user_token
    ADD CONSTRAINT FKce46x6j2j7pmokak9oepb2m16 FOREIGN KEY (user_id) REFERENCES central_user;