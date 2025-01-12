package com.example.automation_service.service;

import com.example.automation_service.client.CampaignServiceClient;
import com.example.automation_service.model.Workflow;
import com.example.automation_service.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import com.example.automation_service.client.SendingServiceClient;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;


@Service
public class WorkflowService {
    @Autowired
    private WorkflowRepository workflowRepo;

    @Autowired
    private CampaignServiceClient campaignServiceClient;

    public void saveWorkflow(Workflow workflow, String to, String subject, String body) {
        if (to != null) {
            workflow.setEmailTo(to);
        }
        if (subject != null) {
            workflow.setEmailSubject(subject);
        }
        if (body != null) {
            workflow.setEmailBody(body);
        }
        workflowRepo.save(workflow);
        System.out.println("Workflow saved: " + workflow);
    }

    @Scheduled(cron = "*/15 * * * * *") // Runs every 15 seconds
    public void processCampaigns() {
        List<Workflow> activeWorkflows = workflowRepo.findByStatus("ACTIVE");
        for (Workflow workflow : activeWorkflows) {
            // Get campaignId directly from the workflow (if it's available)
            Long campaignId = Long.parseLong(workflow.getTriggerValue().split(":")[1].trim()); // Extract campaign ID
            System.out.println("Processing campaign with ID: " + campaignId); // Print directly for debugging
            campaignServiceClient.triggerCampaignSending(campaignId);
        }
    }
}