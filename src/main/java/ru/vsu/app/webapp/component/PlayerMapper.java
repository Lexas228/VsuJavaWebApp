package ru.vsu.app.webapp.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vsu.app.webapp.dto.CurrencyDto;
import ru.vsu.app.webapp.dto.ItemDto;
import ru.vsu.app.webapp.dto.PlayerDto;
import ru.vsu.app.webapp.dto.ProgressDto;
import ru.vsu.app.webapp.entity.CurrencyEntity;
import ru.vsu.app.webapp.entity.ItemEntity;
import ru.vsu.app.webapp.entity.PlayerEntity;
import ru.vsu.app.webapp.entity.ProgressEntity;
import ru.vsu.app.webapp.repo.PlayerRepository;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PlayerMapper implements EntityMapper<PlayerEntity, PlayerDto>{
    private final EntityMapper<CurrencyEntity, CurrencyDto> currencyMapper;
    private final EntityMapper<ProgressEntity, ProgressDto> progressMapper;
    private final EntityMapper<ItemEntity, ItemDto> itemMapper;
    private final PlayerRepository playerRepository;

    @Override
    public PlayerDto mapFromEntity(PlayerEntity entity) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setPlayerId(entity.getId());
        playerDto.setNickname(entity.getNickName());
        playerDto.setProgresses(entity.getProgresses().stream().map(progressMapper::mapFromEntity).toList().toArray(ProgressDto[]::new));
        playerDto.setCurrencies(entity.getCurrencies().stream().collect(Collectors.toMap(currencyEntity -> String.valueOf(currencyEntity.getId()), currencyMapper::mapFromEntity)));
        playerDto.setItems(entity.getItems().stream().collect(Collectors.toMap(itemEntity -> String.valueOf(itemEntity.getId()), itemMapper::mapFromEntity)));
        return playerDto;
    }

    @Override
    public PlayerEntity mapFromDto(PlayerDto dto) {
        PlayerEntity playerEntity = new PlayerEntity();
        if(dto.getPlayerId() != null){
            Optional<PlayerEntity> pe = playerRepository.findById(dto.getPlayerId());
            if(pe.isPresent()){
                playerEntity = pe.get();
            }
        }
        return update(playerEntity, dto);
    }

    @Override
    public PlayerEntity update(PlayerEntity entity, PlayerDto dto) {
        entity.setId(dto.getPlayerId());
        entity.setNickName(dto.getNickname());
        entity.setCurrencies(dto.getCurrencies().values().stream().map(currencyMapper::mapFromDto).collect(Collectors.toList()));
        entity.setProgresses(Arrays.stream(dto.getProgresses()).map(progressMapper::mapFromDto).collect(Collectors.toList()));
        entity.setItems(dto.getItems().values().stream().map(itemMapper::mapFromDto).collect(Collectors.toList()));
        return entity;
    }
}
