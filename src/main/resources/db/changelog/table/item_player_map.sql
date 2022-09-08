--liquibase formatted sql

--changeset shishlov:item_player_map_1
CREATE TABLE vsu_java.item_player_map (
      player_id bigint references vsu_java.player(id),
      item_id bigint references vsu_java.item(id)
);