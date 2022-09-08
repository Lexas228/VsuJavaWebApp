package ru.vsu.app.webapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.vsu.app.webapp.entity.ItemEntity;
import ru.vsu.app.webapp.repo.ItemRepository;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Cacheable(value = "items")
    public ItemEntity getById(Long id){
        return itemRepository.findById(id).orElse(null);
    }

}
