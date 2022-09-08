package ru.vsu.app.webapp.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vsu.app.webapp.dto.CurrencyDto;
import ru.vsu.app.webapp.entity.CurrencyEntity;
import ru.vsu.app.webapp.entity.ResourceEntity;
import ru.vsu.app.webapp.repo.CurrencyRepository;
import ru.vsu.app.webapp.repo.ResourceRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CurrencyMapper implements EntityMapper<CurrencyEntity, CurrencyDto>{
    private final ResourceRepository resourceRepository;
    private final CurrencyRepository currencyRepository;
    @Override
    public CurrencyDto mapFromEntity(CurrencyEntity entity) {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setCount(entity.getCount());
        currencyDto.setId(entity.getId());
        currencyDto.setResourceId(entity.getResource() == null ? null : entity.getResource().getId());
        currencyDto.setName(entity.getName());
        return currencyDto;
    }

    @Override
    public CurrencyEntity mapFromDto(CurrencyDto dto) {
        CurrencyEntity currencyEntity = new CurrencyEntity();
        if(dto.getId() != null){
            Optional<CurrencyEntity> byId = currencyRepository.findById(dto.getId());
            if(byId.isPresent()){
                currencyEntity = byId.get();
            }
        }
        return update(currencyEntity, dto);
    }

    @Override
    public CurrencyEntity update(CurrencyEntity entity, CurrencyDto dto) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCount(dto.getCount());
        entity.setResource(dto.getResourceId() != null ? resourceRepository.findById(dto.getResourceId()).orElse(new ResourceEntity(dto.getResourceId())) : null);
        return entity;
    }
}
