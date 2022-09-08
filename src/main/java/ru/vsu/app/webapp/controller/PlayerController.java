package ru.vsu.app.webapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.vsu.app.webapp.dto.PlayerDto;
import ru.vsu.app.webapp.service.PlayerService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping("/{id}")
    @ResponseBody
    public PlayerDto get(@PathVariable Long id){
        return playerService.getPlayer(id);
    }

    @GetMapping("/{nickName}")
    @ResponseBody
    public PlayerDto get(@PathVariable String nickName){
        return playerService.getPlayerByNickName(nickName);
    }

    @GetMapping
    @ResponseBody
    public List<PlayerDto> getAll(){
        return playerService.getAll();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public PlayerDto edit(@PathVariable Long id, @RequestBody PlayerDto player){
        return playerService.edit(id, player);
    }

    @DeleteMapping("/{id}")
    public PlayerDto delete(@PathVariable Long id){
        return playerService.delete(id);
    }

    @DeleteMapping("")
    public List<PlayerDto> deleteAll(){
        return playerService.deleteAll();
    }

    @PostMapping
    @ResponseBody
    public PlayerDto create(@RequestBody PlayerDto playerDto){
        return playerService.create(playerDto);
    }

    @PostMapping("/all")
    @ResponseBody
    public List<PlayerDto> createAll(@RequestBody PlayerDto[] players){
        return playerService.createAll(Arrays.stream(players).collect(Collectors.toList()));
    }


}
