--liquibase formatted sql

--changeset shishlov:item_1
CREATE TABLE vsu_java.item (
   id bigserial primary key not null,
   resource_id bigint not null,
   count bigint not null,
   level int8 not null
   check ( count >= 0 and level >= 0 )
);