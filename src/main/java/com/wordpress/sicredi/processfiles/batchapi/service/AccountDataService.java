package com.wordpress.sicredi.processfiles.batchapi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wordpress.sicredi.processfiles.batchapi.model.AccountData;
import com.wordpress.sicredi.processfiles.batchapi.repository.AccountDataRepository;
import com.wordpress.sicredi.processfiles.batchapi.utils.DateUtils;

@Service
public class AccountDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDataService.class);

    @Autowired
    private JobLauncher jobLauncher;
	
    @Autowired
    private Job job;

    @Autowired
    private AccountDataRepository accountDataRepository;

    private static final String A_CADA_10_SEG = "*/10 * * * * *";
    private static final String A_CADA_6_HRS_DE_SEG_A_SEX = "0 0 6 * * MON-FRI";
    private static final String TIME_ZONE = "America/Sao_Paulo";

    @Scheduled(cron = A_CADA_6_HRS_DE_SEG_A_SEX, zone = TIME_ZONE)
    public BatchStatus batchExecute() {
        LOGGER.info("Iniciou o Job " + DateUtils.getNow());

        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));

        try {
            JobExecution jobExecution = jobLauncher.run(job, new JobParameters(map));

            while (jobExecution.isRunning()) {
                LOGGER.info("Job em execução");
            }

            LOGGER.info(DateUtils.getNow());
            return jobExecution.getStatus();
        } catch (Exception e) {
            LOGGER.info("Falha ao tentar executar o JOB " + e.getMessage());
            return BatchStatus.FAILED;
        }
    }

    public List<AccountData> findAll() {
        return accountDataRepository.findAll();
    }
}
