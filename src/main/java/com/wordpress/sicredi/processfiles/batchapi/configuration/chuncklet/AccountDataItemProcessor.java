package com.wordpress.sicredi.processfiles.batchapi.configuration.chuncklet;

import com.wordpress.sicredi.processfiles.batchapi.converter.AccountDataConverter;
import com.wordpress.sicredi.processfiles.batchapi.dto.AccountDataDto;
import com.wordpress.sicredi.processfiles.batchapi.model.AccountData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

public class AccountDataItemProcessor implements ItemProcessor<AccountDataDto, AccountData>, StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDataItemProcessor.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        LOGGER.info("Iniciando o PROCESSOR...");
    }

    @Override
    public AccountData process(AccountDataDto accountDataDto) throws Exception {
        return AccountDataConverter.getaccountData(accountDataDto);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.info("Finalizando o PROCESSOR...");
        return ExitStatus.COMPLETED;
    }
}
