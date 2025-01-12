package com.example.automation_service.controller;

import com.example.automation_service.model.Workflow;
import com.example.automation_service.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/automation")
public class AutomationController {

    @Autowired
    private WorkflowService workflowService;

    @GetMapping("/test")
    public String testEndpoint() {
        return "Controller is working!";
    }

    @PostMapping("/triggerEmail")
    public String triggerEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        // Create a new workflow
        Workflow workflow = new Workflow();
        workflow.setTriggerType("manual");
        workflow.setTriggerValue("triggered from API");
        workflow.setActionType("send_email");
        workflow.setStatus("ACTIVE");

        // Save workflow and trigger email
        workflowService.saveWorkflow(workflow, to, subject, body);
        return "Email will be sent Soon!";
    }

    @PostMapping("/triggerCampaign")
    public String triggerCampaign(@RequestParam Long campaignId) {
        // Create a new workflow for the campaign
        Workflow workflow = new Workflow();
        workflow.setTriggerType("campaign");
        workflow.setTriggerValue("Campaign ID: " + campaignId);
        workflow.setActionType("send_campaign");
        workflow.setStatus("ACTIVE");

        // Save workflow
        workflowService.saveWorkflow(workflow, null, null, null);

        // Process campaigns (if needed for immediate processing)
        workflowService.processCampaigns();

        return "Campaign workflow triggered successfully!";
    }
}