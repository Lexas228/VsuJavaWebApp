package ru.vsu.app.webapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import ru.vsu.app.webapp.component.EntityMapper;
import ru.vsu.app.webapp.dto.PlayerDto;
import ru.vsu.app.webapp.entity.PlayerEntity;
import ru.vsu.app.webapp.repo.CurrencyRepository;
import ru.vsu.app.webapp.repo.ItemRepository;
import ru.vsu.app.webapp.repo.PlayerRepository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PlayerServiceImpl implements PlayerService{
    private final PlayerRepository playerRepository;
    private final EntityMapper<PlayerEntity, PlayerDto> playerMapper;
    private final ObjectMapper objectMapper;
    private final CurrencyRepository currencyRepository;
    private final ItemRepository itemRepository;

    @Override
    public PlayerDto getPlayer(Long id) {
        return playerRepository.findById(id).map(playerMapper::mapFromEntity).orElse(null);
    }

    @Override
    public PlayerDto create(PlayerDto playerDto) {
        PlayerEntity playerEntity = playerMapper.mapFromDto(playerDto);
        if(playerEntity.getId() == null) {
            playerEntity.setCurrencies(currencyRepository.saveAll(playerEntity.getCurrencies()));
            playerEntity.setItems(itemRepository.saveAll(playerEntity.getItems()));
            playerEntity.getProgresses().forEach(progressEntity -> progressEntity.setPlayer(playerEntity));
        }else{
            playerEntity.getProgresses().forEach(progressEntity -> progressEntity.setPlayer(playerEntity));
        }
        return playerMapper.mapFromEntity(playerRepository.save(playerEntity));
    }

    @Override
    public List<PlayerDto> createAll(List<PlayerDto> playerDtos){
        return playerDtos.stream().map(this::create).collect(Collectors.toList());
    }

    @Override
    public PlayerDto edit(Long id, PlayerDto playerDto) {
        playerDto.setPlayerId(id);
        PlayerEntity playerEntity = playerMapper.mapFromDto(playerDto);
        return playerMapper.mapFromEntity(playerRepository.save(playerEntity));
    }

    @Override
    public List<PlayerDto> getAll() {
        return playerRepository.findAll().stream().map(playerMapper::mapFromEntity).collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public List<PlayerDto> createFromJsonString(String json) {
        PlayerDto[] playerDtos = objectMapper.readValue(json, PlayerDto[].class);
        return Arrays.stream(playerDtos).map(playerMapper::mapFromDto).map(playerRepository::save).map(playerMapper::mapFromEntity).collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public List<PlayerDto> createFromFile(File file) {
        PlayerDto[] playerDtos = objectMapper.readValue(file, PlayerDto[].class);
        List<PlayerEntity> collect = Arrays.stream(playerDtos).map(playerMapper::mapFromDto).collect(Collectors.toList());
        return playerRepository.saveAll(collect).stream().map(playerMapper::mapFromEntity).collect(Collectors.toList());
    }

    @SneakyThrows
    @PostConstruct
    public void hotCache(){
        File file = ResourceUtils.getFile("classpath:players.json");
        createFromFile(file);
    }
}
