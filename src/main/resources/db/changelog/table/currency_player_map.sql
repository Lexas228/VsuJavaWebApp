--liquibase formatted sql

--changeset shishlov:currency_player_map_1
CREATE TABLE vsu_java.currency_player_map (
   player_id bigint references vsu_java.player(id),
   currency_id bigint references vsu_java.currency(id)
);