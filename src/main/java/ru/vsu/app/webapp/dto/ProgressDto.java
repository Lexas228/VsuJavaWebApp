package ru.vsu.app.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ProgressDto {
    private Long id;
    private Long playerId;
    private Long resourceId;
    private Long score;
    private Long maxScore;
}
