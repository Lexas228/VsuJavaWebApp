package ru.vsu.app.webapp.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vsu.app.webapp.dto.ItemDto;
import ru.vsu.app.webapp.entity.CurrencyEntity;
import ru.vsu.app.webapp.entity.ItemEntity;
import ru.vsu.app.webapp.entity.ResourceEntity;
import ru.vsu.app.webapp.repo.ItemRepository;
import ru.vsu.app.webapp.repo.ResourceRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemMapper implements EntityMapper<ItemEntity, ItemDto>{
    private final ResourceRepository resourceRepository;
    private final ItemRepository itemRepository;
    @Override
    public ItemDto mapFromEntity(ItemEntity entity) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(entity.getId());
        itemDto.setCount(entity.getCount());
        itemDto.setLevel(entity.getLevel());
        itemDto.setResourceId(entity.getResource() != null ? entity.getResource().getId() : null);
        return itemDto;
    }

    @Override
    public ItemEntity mapFromDto(ItemDto dto) {
        ItemEntity itemEntity = new ItemEntity();
        if(dto.getId() != null){
            Optional<ItemEntity> byId = itemRepository.findById(dto.getId());
            if(byId.isPresent()){
                itemEntity = byId.get();
            }
        }
        return update(itemEntity, dto);
    }

    @Override
    public ItemEntity update(ItemEntity entity, ItemDto dto) {
        entity.setId(dto.getId());
        entity.setCount(dto.getCount());
        entity.setResource(dto.getResourceId() != null ? resourceRepository.findById(dto.getResourceId()).orElse(new ResourceEntity(dto.getResourceId())) : null);
        entity.setLevel(dto.getLevel());
        return entity;
    }
}
