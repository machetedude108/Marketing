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
}