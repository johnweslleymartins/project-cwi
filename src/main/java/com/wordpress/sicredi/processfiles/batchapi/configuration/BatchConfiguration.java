package com.wordpress.sicredi.processfiles.batchapi.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wordpress.sicredi.processfiles.batchapi.configuration.chuncklet.AccountDataItemProcessor;
import com.wordpress.sicredi.processfiles.batchapi.configuration.chuncklet.AccountDataItemReader;
import com.wordpress.sicredi.processfiles.batchapi.configuration.chuncklet.AccountDataItemWriter;
import com.wordpress.sicredi.processfiles.batchapi.configuration.tasklet.AccountDataValidateTasklet;
import com.wordpress.sicredi.processfiles.batchapi.dto.AccountDataDto;
import com.wordpress.sicredi.processfiles.batchapi.model.AccountData;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory
                .get("accountDataJob")
                .start(accountDataValidateTaskletStep())
                .next(accountDataEnrichmentChunckletStep(accountDataItemReader(),
                        accountDataItemProcessor(),
                        accountDataWrite()))
                .build();
    }

    @Bean
    public Step accountDataValidateTaskletStep() {
        return stepBuilderFactory
                .get("accountDataValidateTaskletStep")
                .tasklet(new AccountDataValidateTasklet("account-data-import"))
                .build();
    }

    @Bean
    public Step accountDataEnrichmentChunckletStep(
            ItemReader<AccountDataDto> accountDataItemReader,
            ItemProcessor<AccountDataDto, AccountData> accountDataItemProcessor,
            ItemWriter<AccountData> accountDataWrite) {

        return stepBuilderFactory
                .get("accountDataEnrichmentChunckletStep")
                .<AccountDataDto, AccountData>chunk(5)
                .reader(accountDataItemReader)
                .processor(accountDataItemProcessor)
                .writer(accountDataWrite)
                .build();
    }

    @Bean
    public ItemReader<AccountDataDto> accountDataItemReader() {
        return new AccountDataItemReader();
    }

    @Bean
    public ItemProcessor<AccountDataDto, AccountData> accountDataItemProcessor() {
        return new AccountDataItemProcessor();
    }

    @Bean
    public ItemWriter<AccountData> accountDataWrite() {
        return new AccountDataItemWriter();
    }
}

