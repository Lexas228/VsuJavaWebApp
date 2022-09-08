package ru.vsu.app.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class PlayerDto {
    private Long playerId;
    private String nickname;
    private ProgressDto[] progresses;
    private Map<String, CurrencyDto> currencies;
    private Map<String, ItemDto> items;
}
