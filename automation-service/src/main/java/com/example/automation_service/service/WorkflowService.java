package com.example.automation_service.service;

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
    private SendingServiceClient sendingServiceClient;

    public void saveWorkflow(Workflow workflow, String to, String subject, String body) {
        workflow.setEmailTo(to);
        workflow.setEmailSubject(subject);
        workflow.setEmailBody(body);
        workflowRepo.save(workflow);
    }

    @Scheduled(cron = "*/15 * * * * *") // Runs every 15 seconds
    public void processEmails() {
        List<Workflow> activeWorkflows = workflowRepo.findByStatus("ACTIVE");
        for (Workflow workflow : activeWorkflows) {
            sendingServiceClient.sendEmail(
                    workflow.getEmailTo(),
                    workflow.getEmailSubject(),
                    workflow.getEmailBody()
            );
        }
    }
}