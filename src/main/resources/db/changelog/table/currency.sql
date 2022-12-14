--liquibase formatted sql

--changeset shishlov:currency_1
CREATE TABLE vsu_java.currency (
      id bigserial primary key not null,
      resource_id bigint not null,
      name varchar(100) not null,
      count bigint not null,
      check ( count >= 0 )
);