package ru.vsu.app.webapp.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vsu.app.webapp.dto.ProgressDto;
import ru.vsu.app.webapp.entity.PlayerEntity;
import ru.vsu.app.webapp.entity.ProgressEntity;
import ru.vsu.app.webapp.entity.ResourceEntity;
import ru.vsu.app.webapp.repo.PlayerRepository;
import ru.vsu.app.webapp.repo.ProgressRepository;
import ru.vsu.app.webapp.repo.ResourceRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProgressMapper implements EntityMapper<ProgressEntity, ProgressDto>{
    private final PlayerRepository playerRepository;
    private final ResourceRepository resourceRepository;
    private final ProgressRepository progressRepository;
    @Override
    public ProgressDto mapFromEntity(ProgressEntity entity) {
        ProgressDto progressDto = new ProgressDto();
        progressDto.setId(entity.getId());
        progressDto.setScore(entity.getScore());
        progressDto.setMaxScore(entity.getMaxScore());
        progressDto.setPlayerId(entity.getPlayer() != null ? entity.getPlayer().getId() : null);
        progressDto.setResourceId(entity.getResource() != null ? entity.getResource().getId() : null);
        return progressDto;
    }

    @Override
    public ProgressEntity mapFromDto(ProgressDto dto) {
        ProgressEntity progressEntity = new ProgressEntity();
        if(dto.getId() != null){
            Optional<ProgressEntity> pre = progressRepository.findById(dto.getId());
            if(pre.isPresent()){
                progressEntity = pre.get();
            }
        }
        return update(progressEntity, dto);
   }

    @Override
    public ProgressEntity update(ProgressEntity entity, ProgressDto dto) {
        entity.setId(dto.getId());
        entity.setPlayer(dto.getPlayerId() != null ? playerRepository.findById(dto.getPlayerId()).orElse(null) : null);
        entity.setResource(dto.getResourceId() != null ? resourceRepository.findById(dto.getResourceId()).orElse(new ResourceEntity(dto.getResourceId())) : null);
        entity.setScore(dto.getScore());
        entity.setMaxScore(dto.getMaxScore());
        return entity;
    }
}
