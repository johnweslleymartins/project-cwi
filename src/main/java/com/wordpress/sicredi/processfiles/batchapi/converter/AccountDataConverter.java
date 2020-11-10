package com.wordpress.sicredi.processfiles.batchapi.converter;

import java.util.ArrayList;
import java.util.List;

import com.wordpress.sicredi.processfiles.batchapi.dto.AccountDataDto;
import com.wordpress.sicredi.processfiles.batchapi.model.AccountData;
import com.wordpress.sicredi.processfiles.batchapi.utils.AccountDataUtils;

public class AccountDataConverter {

    public static String[] getErrorToStringArray(String message) {
        List<String> listOut = new ArrayList<>();
        listOut.add(message);
        return listOut.stream().toArray(String[]::new);
    }

    public static String[] accountDataToStringArray(AccountDataDto accountData) {
        List<String> listOut = new ArrayList<>();
        listOut.add(accountData.getAgencia());
        listOut.add(accountData.getConta());
        listOut.add(accountData.getSaldo());
        listOut.add(accountData.getStatus());
        listOut.add(accountData.getInvalid());
        return listOut.stream().toArray(String[]::new);
    }

    public static AccountData getaccountData(AccountDataDto accountDataDto) {
        AccountData accountData = new AccountData();
        accountData.setAgencia(accountDataDto.getAgencia());
        accountData.setConta(accountDataDto.getConta());
        accountData.setSaldo(AccountDataUtils.convertToBigDecimal(accountDataDto.getSaldo()));
        accountData.setStatus(AccountDataUtils.convertToStatusEnum(accountDataDto.getStatus()));
        accountData.setProcessado(AccountDataUtils.convertToProcessadoEnum(Boolean.TRUE));
        return accountData;
    }
}