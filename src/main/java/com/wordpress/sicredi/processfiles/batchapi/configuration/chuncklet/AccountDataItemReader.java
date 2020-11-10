package com.wordpress.sicredi.processfiles.batchapi.configuration.chuncklet;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;

import com.wordpress.sicredi.processfiles.batchapi.dto.AccountDataDto;

public class AccountDataItemReader implements ItemReader<AccountDataDto>, StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDataItemReader.class);
    private Iterator<AccountDataDto> accountDataDtoIterator;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext ec = stepExecution.getJobExecution().getExecutionContext();
        List<AccountDataDto> accountDataDtoList = (List<AccountDataDto>) ec.get("accountDataInList");
        this.accountDataDtoIterator = accountDataDtoList.iterator();
        LOGGER.info("Iniciando o READER...");
    }

    @Override
    public AccountDataDto read() {
        if (this.accountDataDtoIterator != null && this.accountDataDtoIterator.hasNext()) {
            return this.accountDataDtoIterator.next();
        }

        return null;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.info("Finalizando o READER...");
        return ExitStatus.COMPLETED;
    }
}
