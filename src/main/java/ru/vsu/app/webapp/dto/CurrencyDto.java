package ru.vsu.app.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class CurrencyDto {
    private Long id;
    private Long resourceId;
    private String name;
    private Long count;
}
