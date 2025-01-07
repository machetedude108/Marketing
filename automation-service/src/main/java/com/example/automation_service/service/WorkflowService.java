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
        // Save the workflow
        workflowRepo.save(workflow);

        // Schedule the email sending
        scheduleEmail(to, subject, body);
    }
    public void scheduleEmail(String to, String subject, String body) {
        try {
            Thread.sleep(1000);
            sendingServiceClient.sendEmail(to, subject, body);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
