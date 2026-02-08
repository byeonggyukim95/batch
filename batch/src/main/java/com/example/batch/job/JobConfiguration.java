package com.example.batch.job;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfiguration {

    private final JobRepository jobRepository;

    private final Step insertCoinCandleStep;
    private final Step sendCoinCandleStep;

    public JobConfiguration(
            JobRepository jobRepository,
            Step insertCoinCandleStep,
            Step sendCoinCandleStep
            ) {
        this.jobRepository = jobRepository;
        this.insertCoinCandleStep = insertCoinCandleStep;
        this.sendCoinCandleStep = sendCoinCandleStep;
    }

    @Bean
    public Job coinCandlesJob() {
        return new JobBuilder("coinCandlesJob", jobRepository)
                .start(insertCoinCandleStep)
                .next(sendCoinCandleStep)
                .build();
    }

}
