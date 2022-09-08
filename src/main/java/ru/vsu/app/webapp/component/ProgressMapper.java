package ru.vsu.app.webapp.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vsu.app.webapp.dto.ProgressDto;
import ru.vsu.app.webapp.entity.ProgressEntity;
import ru.vsu.app.webapp.repo.PlayerRepository;
import ru.vsu.app.webapp.service.ProgressService;

@Component
@RequiredArgsConstructor
public class ProgressMapper implements EntityMapper<ProgressEntity, ProgressDto>{
    private final PlayerRepository playerRepository;
    private final ProgressService progressService;
    @Override
    public ProgressDto mapFromEntity(ProgressEntity entity) {
        ProgressDto progressDto = new ProgressDto();
        progressDto.setId(entity.getId());
        progressDto.setScore(entity.getScore());
        progressDto.setMaxScore(entity.getMaxScore());
        progressDto.setPlayerId(entity.getPlayer() != null ? entity.getPlayer().getId() : null);
        progressDto.setResourceId(entity.getResourceId());
        return progressDto;
    }

    @Override
    public ProgressEntity mapFromDto(ProgressDto dto) {
        ProgressEntity progressEntity = new ProgressEntity();
        if(dto.getId() != null){
            ProgressEntity pre = progressService.getById(dto.getId());
            if(pre != null){
                progressEntity = pre;
            }
        }
        return update(progressEntity, dto);
   }

    @Override
    public ProgressEntity update(ProgressEntity entity, ProgressDto dto) {
        entity.setId(dto.getId());
        entity.setPlayer(dto.getPlayerId() != null ? playerRepository.findById(dto.getPlayerId()).orElse(null) : null);
        entity.setResourceId(dto.getResourceId());
        entity.setScore(dto.getScore());
        entity.setMaxScore(dto.getMaxScore());
        return entity;
    }
}
