--liquibase formatted sql

--changeset shishlov:resource_id
CREATE TABLE vsu_java.resource (
     id bigserial primary key not null
);