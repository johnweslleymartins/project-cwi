package com.wordpress.sicredi.processfiles.batchapi.dto;

import java.io.Serializable;

public class AccountDataDto implements Serializable {

    private static final long serialVersionUID = 1l;

    private String agencia;
    private String conta;
    private String saldo;
    private String status;
    private String invalid;

    public AccountDataDto() {}

    public AccountDataDto(String invalid) {
        this.invalid = invalid;
    }

    public AccountDataDto(String agencia, String conta, String saldo, String status) {
        this.agencia = agencia;
        this.conta = conta;
        this.saldo = saldo;
        this.status = status;
    }

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getInvalid() {
		return invalid;
	}

	public void setInvalid(String invalid) {
		this.invalid = invalid;
	}
}
