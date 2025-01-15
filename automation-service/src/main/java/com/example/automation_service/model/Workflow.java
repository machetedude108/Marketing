package com.example.automation_service.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Workflow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String triggerType;
    private String triggerValue;
    private String actionType;
    private String status;
    private String emailTo;
    private String emailSubject;
    private String emailBody;

    // New fields for scheduling
    private String cronExpression;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Integer repeatCount;
    private Long intervalInSeconds;

    public Workflow(String emailTo, String emailSubject, String emailBody) {
        this.emailTo = emailTo;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;
    }
}
