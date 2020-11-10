package com.wordpress.sicredi.processfiles.batchapi.configuration.chuncklet;

import com.wordpress.sicredi.processfiles.batchapi.model.AccountData;
import com.wordpress.sicredi.processfiles.batchapi.repository.AccountDataRepository;
import com.wordpress.sicredi.processfiles.batchapi.utils.CsvFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class AccountDataItemWriter implements ItemWriter<AccountData>, StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDataItemWriter.class);
    private CsvFileUtils csvSavedAccountData;

    @Autowired
    private AccountDataRepository accountDataRepository;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.csvSavedAccountData = new CsvFileUtils("savedAccountData", false);

        LOGGER.info("Finalizando o WRITER...");
    }

    @Override
    public void write(List<? extends AccountData> accountDataOutList) throws Exception {
        List<? extends AccountData> savedAccountDataList = this.accountDataRepository.saveAll(accountDataOutList);

        savedAccountDataList.stream().forEach(accountData -> {
            try {
                this.csvSavedAccountData.writer(accountData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        try {
            this.csvSavedAccountData.closeWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("Finalizando o WRITER...");
        return ExitStatus.COMPLETED;
    }
}
