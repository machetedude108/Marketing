package com.example.automation_service.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ScheduleRequest {
    private String cronExpression;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Integer repeatCount;
    private Long intervalInSeconds;
}
