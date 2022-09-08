package ru.vsu.app.webapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.vsu.app.webapp.entity.CurrencyEntity;
import ru.vsu.app.webapp.repo.CurrencyRepository;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    @Cacheable(value = "currencies")
    public CurrencyEntity getById(Long id){
       return currencyRepository.findById(id).orElse(null);
    }
}
