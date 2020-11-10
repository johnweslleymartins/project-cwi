package com.wordpress.sicredi.processfiles.batchapi.configuration.tasklet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.wordpress.sicredi.processfiles.batchapi.dto.AccountDataDto;
import com.wordpress.sicredi.processfiles.batchapi.utils.CsvFileUtils;
import com.wordpress.sicredi.processfiles.batchapi.validate.AccountDataValidate;

public class AccountDataValidateTasklet implements Tasklet, StepExecutionListener {
    private List<AccountDataDto> accountDataDtoList;
    private String fileName;

    public AccountDataValidateTasklet() {}

    public AccountDataValidateTasklet(String  fileName) {
        this.fileName = fileName;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.accountDataDtoList = new ArrayList<>();
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        CsvFileUtils csvIn = new CsvFileUtils(this.fileName, true);

        AccountDataDto accountDataDto = csvIn.read();

        while (accountDataDto != null) {
            this.accountDataDtoList.add(accountDataDto);
            accountDataDto = csvIn.read();
        }

        csvIn.closeReader();

        this.accountDataDtoList = AccountDataValidate.validate(this.accountDataDtoList);

        if (accountDataDtoList.isEmpty()) {
            throw new RuntimeException("A lista de contas validos está vazia!");
        }

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution
                .getJobExecution()
                .getExecutionContext()
                .put("accountDataInList", this.accountDataDtoList);
        return ExitStatus.COMPLETED;
    }
}

