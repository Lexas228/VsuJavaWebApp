package ru.vsu.app.webapp.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vsu.app.webapp.dto.CurrencyDto;
import ru.vsu.app.webapp.entity.CurrencyEntity;
import ru.vsu.app.webapp.service.CurrencyService;

@Component
@RequiredArgsConstructor
public class CurrencyMapper implements EntityMapper<CurrencyEntity, CurrencyDto>{
    private final CurrencyService currencyService;
    @Override
    public CurrencyDto mapFromEntity(CurrencyEntity entity) {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setCount(entity.getCount());
        currencyDto.setId(entity.getId());
        currencyDto.setResourceId(entity.getResourceId());
        currencyDto.setName(entity.getName());
        return currencyDto;
    }

    @Override
    public CurrencyEntity mapFromDto(CurrencyDto dto) {
        CurrencyEntity currencyEntity = new CurrencyEntity();
        if(dto.getId() != null){
            CurrencyEntity byId = currencyService.getById(dto.getId());
            currencyEntity = byId == null ? currencyEntity : byId;
        }
        return update(currencyEntity, dto);
    }

    @Override
    public CurrencyEntity update(CurrencyEntity entity, CurrencyDto dto) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCount(dto.getCount());
        entity.setResourceId(dto.getResourceId());
        return entity;
    }
}
