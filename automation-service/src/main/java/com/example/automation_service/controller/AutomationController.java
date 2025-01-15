package com.example.automation_service.controller;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.automation_service.model.Workflow;
import com.example.automation_service.service.WorkflowService;
import com.example.automation_service.dto.ScheduleRequest;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/automation")
public class AutomationController {
    @Autowired
    private WorkflowService workflowService;

    @PostMapping("/triggerCampaign")
    public String triggerCampaign(
            @RequestParam Long campaignId,
            @RequestBody ScheduleRequest scheduleRequest) throws SchedulerException {

        Workflow workflow = new Workflow();
        workflow.setTriggerType("campaign");
        workflow.setTriggerValue("Campaign ID: " + campaignId);
        workflow.setActionType("send_campaign");
        workflow.setStatus("ACTIVE");

        workflowService.saveWorkflow(workflow, scheduleRequest);
        return "Campaign workflow scheduled successfully!";
    }

    @PutMapping("/{workflowId}/status")
    public String updateWorkflowStatus(
            @PathVariable UUID workflowId,
            @RequestParam String status) throws SchedulerException {
        workflowService.updateWorkflowStatus(workflowId, status);
        return "Workflow status updated successfully!";
    }
}