package com.example.automation_service;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.quartz.JobBuilder;
import com.example.automation_service.job.CampaignJob;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
public class QuartzConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public JobDetail campaignJobDetail() {
        return JobBuilder.newJob(CampaignJob.class)
                .withIdentity("campaignJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Scheduler scheduler(JobDetail campaignJobDetail, SpringBeanJobFactory springBeanJobFactory) throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.setJobFactory(springBeanJobFactory);
        scheduler.start();
        return scheduler;
    }
}