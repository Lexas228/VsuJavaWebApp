package ru.vsu.app.webapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.vsu.app.webapp.entity.ProgressEntity;
import ru.vsu.app.webapp.repo.ProgressRepository;

@Service
@RequiredArgsConstructor
public class ProgressService {
    private final ProgressRepository progressRepository;

    @Cacheable(value = "progresses")
    public ProgressEntity getById(Long id){
        return progressRepository.findById(id).orElse(null);
    }
}
