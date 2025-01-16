package com.example.automation_service.service;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.automation_service.model.Workflow;
import com.example.automation_service.repository.WorkflowRepository;
import com.example.automation_service.job.CampaignJob;
import com.example.automation_service.dto.ScheduleRequest;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkflowService {
    @Autowired
    private WorkflowRepository workflowRepo;

    @Autowired
    private Scheduler scheduler;

    public void saveWorkflow(Workflow workflow, ScheduleRequest scheduleRequest) throws SchedulerException {
        // Validate schedule request
        if (scheduleRequest.getCronExpression() == null && scheduleRequest.getIntervalInSeconds() == null) {
            scheduleRequest.setIntervalInSeconds(3600L); // Default to 1 hour if neither is specified
        }

        // Save the workflow
        workflow.setCronExpression(scheduleRequest.getCronExpression());
        workflow.setStartAt(scheduleRequest.getStartAt());
        workflow.setEndAt(scheduleRequest.getEndAt());
        workflow.setRepeatCount(scheduleRequest.getRepeatCount());
        workflow.setIntervalInSeconds(scheduleRequest.getIntervalInSeconds());

        Workflow savedWorkflow = workflowRepo.save(workflow);

        // Schedule the job based on the scheduling type
        scheduleJob(savedWorkflow, scheduleRequest);
    }

    private void scheduleJob(Workflow workflow, ScheduleRequest scheduleRequest) throws SchedulerException {
        // Validate that either cronExpression or intervalInSeconds is provided
        if (workflow.getCronExpression() == null && workflow.getIntervalInSeconds() == null) {
            throw new IllegalArgumentException("Either cronExpression or intervalInSeconds must be provided");
        }

        JobDetail jobDetail = JobBuilder.newJob(CampaignJob.class)
                .withIdentity("campaign-" + workflow.getId())
                .usingJobData("workflowId", workflow.getId().toString())
                .build();

        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger()
                .withIdentity("trigger-" + workflow.getId());

        if (workflow.getStartAt() != null) {
            triggerBuilder.startAt(Date.from(workflow.getStartAt().atZone(ZoneId.systemDefault()).toInstant()));
        } else {
            triggerBuilder.startNow();
        }

        if (workflow.getEndAt() != null) {
            triggerBuilder.endAt(Date.from(workflow.getEndAt().atZone(ZoneId.systemDefault()).toInstant()));
        }

        if (workflow.getCronExpression() != null && !workflow.getCronExpression().isEmpty()) {
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(workflow.getCronExpression()));
        } else {
            // Default to 1 hour interval if not specified
            int intervalSeconds = workflow.getIntervalInSeconds() != null ?
                    workflow.getIntervalInSeconds().intValue() : 3600;

            SimpleScheduleBuilder simpleSchedule = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(intervalSeconds);

            if (workflow.getRepeatCount() != null && workflow.getRepeatCount() > 0) {
                simpleSchedule.withRepeatCount(workflow.getRepeatCount());
            } else {
                simpleSchedule.repeatForever();
            }

            triggerBuilder.withSchedule(simpleSchedule);
        }

        scheduler.scheduleJob(jobDetail, triggerBuilder.build());
    }


    public void updateWorkflowStatus(UUID workflowId, String status) throws SchedulerException {
        Workflow workflow = workflowRepo.findById(workflowId)
                .orElseThrow(() -> new RuntimeException("Workflow not found"));
        workflow.setStatus(status);
        workflowRepo.save(workflow);

        if ("INACTIVE".equals(status)) {
            scheduler.deleteJob(JobKey.jobKey("campaign-" + workflowId));
        }
    }
    public List<Workflow> getAllWorkflows() {
        return workflowRepo.findAll();
    }

    public List<Workflow> getActiveWorkflows() {
        return workflowRepo.findByStatus("ACTIVE");
    }

    public Optional<Workflow> getWorkflowById(UUID id) {
        return workflowRepo.findById(id);
    }
}