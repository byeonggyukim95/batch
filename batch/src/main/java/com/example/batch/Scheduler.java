package com.example.batch;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Scheduler {

    private final JobOperator jobOperator;
    private final Job coinCandlesJob;

    public Scheduler(JobOperator jobOperator, Job coinCandlesJob) {
        this.jobOperator = jobOperator;
        this.coinCandlesJob = coinCandlesJob;
    }

    @Scheduled(cron = "${cron.coinCandlesJob}")
    public void runCoinCandlesJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

        jobOperator.start(coinCandlesJob, jobParameters);
    }


}
