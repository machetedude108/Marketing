package com.automationservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;


@Entity
public class Workflow {
    @Id
    private UUID id;
    private String triggerType;
    private String triggerValue;
    private String actionType;
    private String status;

    public String getActionType() {
        return actionType;
    }
    // Getters and setters
}