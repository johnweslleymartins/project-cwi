package com.wordpress.sicredi.processfiles.batchapi.validate;

import com.wordpress.sicredi.processfiles.batchapi.converter.AccountDataConverter;
import com.wordpress.sicredi.processfiles.batchapi.dto.AccountDataDto;
import com.wordpress.sicredi.processfiles.batchapi.utils.CsvFileUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountDataValidate {

    public AccountDataValidate() {}

    public static List<AccountDataDto> validate(List<AccountDataDto> accountDataDtoList) throws IOException {

        List<AccountDataDto> validList = new ArrayList<>();
        List<AccountDataDto> invalidList = new ArrayList<>();

        accountDataDtoList.stream().forEach(dto -> {

            if(isValid(dto)){
                validList.add(dto);
            }else{
                invalidList.add(dto);
            }
        });

        createCsvToInvalidList(invalidList);
        return validList;
    }

    private static void createCsvToInvalidList(List<AccountDataDto> invalidList) throws IOException {

        if(!invalidList.isEmpty()){

            CsvFileUtils csvOutInvalid = new CsvFileUtils("invalid-import", false);

            invalidList.stream().forEach(accountData -> {
                try {
                    csvOutInvalid.writerError(AccountDataConverter.accountDataToStringArray(accountData));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            csvOutInvalid.closeWriter();
        }
    }

    public void emptyFile(LocalDateTime localDateTime) throws IOException {

        CsvFileUtils emptyCSV = new CsvFileUtils("empty-file",false);

        try {
            emptyCSV.writerError(AccountDataConverter.getErrorToStringArray("Tentativa de Import de arquivo vazio!"));
            emptyCSV.closeWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValid(AccountDataDto accountDataDto) {
    	if (StringUtils.isBlank(accountDataDto.getAgencia()) || 
    			StringUtils.length(accountDataDto.getAgencia()) != 4) {
    		return false;
		}
        
    	if (StringUtils.isBlank(accountDataDto.getConta()) || 
    			StringUtils.length(accountDataDto.getConta().replaceAll ("-", "")) != 6) {
    		return false;		
    	}
    	
    	if (!StringUtils.containsAny(accountDataDto.getStatus(), 'A', 'I', 'B', 'P')) {
    		return false;
		}
    	
    	return true;
    }
}