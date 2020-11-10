package com.wordpress.sicredi.processfiles.batchapi.utils;

import java.math.BigDecimal;

import com.wordpress.sicredi.processfiles.batchapi.enums.ProcessadoEnum;
import com.wordpress.sicredi.processfiles.batchapi.enums.StatusEnum;

public class AccountDataUtils {
	
	public static ProcessadoEnum convertToProcessadoEnum(Boolean processado) {		
		ProcessadoEnum processadoEnum = null;		
		try {
			if (processado) {
				processadoEnum = ProcessadoEnum.SIM;	
			} else {
				processadoEnum = ProcessadoEnum.NAO;	
			}
			return processadoEnum;
        }catch(Exception e){
            return null;
        }		
	}

	public static StatusEnum convertToStatusEnum(String status) {		
		StatusEnum statusEnum = null;		
		try {
			switch (status) {
			case "A":				
				statusEnum = StatusEnum.A;
				break;
			case "I":
				statusEnum = StatusEnum.I;
				break;
			case "B":
				statusEnum = StatusEnum.B;
				break;
			case "P":
				statusEnum = StatusEnum.P;
				break;
			default:
				break;
			}			
			return statusEnum;
        }catch(Exception e){
            return null;
        }		
	}

    public static BigDecimal convertToBigDecimal(String valor) {
        try {
            return new BigDecimal(valor);
        }catch(Exception e){
            return null;
        }
    }

    public static Integer convertToInteger(String ano) {
        try {
            return Integer.parseInt(ano);
        }catch(Exception e){
            return null;
        }
    }
}