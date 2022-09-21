package ru.vsu.app.webapp.service;

import ru.vsu.app.webapp.dto.PlayerDto;
import ru.vsu.app.webapp.entity.PlayerEntity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface PlayerService {
    PlayerDto getPlayer(Long id);
    PlayerDto getPlayerByNickName(String name);
    PlayerDto delete(Long id);
    PlayerDto delete(String nickName);
    List<PlayerDto> deleteAll();
    PlayerDto create(PlayerDto playerDto);
    List<PlayerDto> createAll(List<PlayerDto> playerDto);
    PlayerDto edit(Long id, PlayerDto playerDto);
    List<PlayerDto> getAll();
}
