package ru.vsu.app.webapp.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vsu.app.webapp.dto.ItemDto;
import ru.vsu.app.webapp.entity.ItemEntity;
import ru.vsu.app.webapp.service.ItemService;

@Component
@RequiredArgsConstructor
public class ItemMapper implements EntityMapper<ItemEntity, ItemDto>{
    private final ItemService itemService;
    @Override
    public ItemDto mapFromEntity(ItemEntity entity) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(entity.getId());
        itemDto.setCount(entity.getCount());
        itemDto.setLevel(entity.getLevel());
        itemDto.setResourceId(entity.getResourceId());
        return itemDto;
    }

    @Override
    public ItemEntity mapFromDto(ItemDto dto) {
        ItemEntity itemEntity = new ItemEntity();
        if(dto.getId() != null){
            ItemEntity byId = itemService.getById(dto.getId());
            if(byId != null){
                itemEntity = byId;
            }
        }
        return update(itemEntity, dto);
    }

    @Override
    public ItemEntity update(ItemEntity entity, ItemDto dto) {
        entity.setId(dto.getId());
        entity.setCount(dto.getCount());
        entity.setResourceId(dto.getResourceId());
        entity.setLevel(dto.getLevel());
        return entity;
    }
}
