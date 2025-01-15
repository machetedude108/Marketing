package com.example.automation_service.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.automation_service.client.CampaignServiceClient;
import com.example.automation_service.model.Workflow;
import com.example.automation_service.repository.WorkflowRepository;
import java.util.Optional;
import java.util.UUID;

@Component
public class CampaignJob implements Job {
    @Autowired
    private CampaignServiceClient campaignServiceClient;

    @Autowired
    private WorkflowRepository workflowRepo;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (workflowRepo == null) {
            throw new JobExecutionException("WorkflowRepository not autowired properly");
        }
        if (campaignServiceClient == null) {
            throw new JobExecutionException("CampaignServiceClient not autowired properly");
        }
        JobDataMap data = context.getJobDetail().getJobDataMap();
        String workflowIdStr = data.get("workflowId").toString();
        UUID workflowId = UUID.fromString(workflowIdStr);

        Optional<Workflow> workflowOpt = workflowRepo.findById(workflowId);
        if (workflowOpt.isPresent()) {
            Workflow workflow = workflowOpt.get();
            if ("ACTIVE".equals(workflow.getStatus())) {
                Long campaignId = Long.parseLong(workflow.getTriggerValue().split(":")[1].trim());
                campaignServiceClient.triggerCampaignSending(campaignId);
            }
        }
    }
}
