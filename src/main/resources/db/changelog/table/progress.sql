--liquibase formatted sql

--changeset shishlov:progress_1
CREATE TABLE vsu_java.progress (
     id bigserial primary key not null,
     player_id bigint references vsu_java.player(id),
     resource_id bigint not null,
     score bigint,
     max_score bigint,
     check ( score is null and max_score is null
                 or score is not null and max_score is not null
                 or score is not null and max_score is not null and score <= progress.max_score)
);