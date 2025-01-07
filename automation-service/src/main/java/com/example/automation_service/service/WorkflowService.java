package com.automationservice.service;

import com.automationservice.model.Workflow;
import com.automationservice.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import com.automationservice.client.SendingServiceClient;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;


@Service
public class WorkflowService {
    @Autowired
    private WorkflowRepository workflowRepo;

    @Autowired
    private SendingServiceClient sendingServiceClient;

    @Scheduled(cron = "0 0 12 * * ?") // Trigger every day at noon
    public void checkAndTriggerWorkflows() {
        List<Workflow> activeWorkflows = workflowRepo.findByStatus("ACTIVE");
        activeWorkflows.forEach(workflow -> {
            // Check trigger conditions and perform actions
            if ("send_email".equals(workflow.getActionType())) {
                sendingServiceClient.sendEmail(workflow);
            }
        });
    }
}
