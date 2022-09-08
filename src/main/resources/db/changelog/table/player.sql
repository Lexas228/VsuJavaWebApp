--liquibase formatted sql

--changeset shishlov:player_1
CREATE TABLE vsu_java.player (
    id bigserial primary key not null,
    nickname varchar(100) unique not null
);