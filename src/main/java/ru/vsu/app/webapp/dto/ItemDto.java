package ru.vsu.app.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ItemDto {
    private Long id;
    private Long resourceId;
    private Long count;
    private Integer level;
}
