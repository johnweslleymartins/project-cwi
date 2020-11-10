package com.wordpress.sicredi.processfiles.batchapi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wordpress.sicredi.processfiles.batchapi.enums.ProcessadoEnum;
import com.wordpress.sicredi.processfiles.batchapi.enums.StatusEnum;

@Entity
public class AccountData implements Serializable {

    private static final long serialVersionUID = 2l;

    @Id
    @GeneratedValue
    private Long id;
    private String agencia;
    private String conta;
    private BigDecimal saldo;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @Enumerated(EnumType.STRING)
    private ProcessadoEnum processado;
    
    public AccountData() {}

    public AccountData(Long id, String agencia, String conta, BigDecimal saldo, StatusEnum status, Date dataCadastro, ProcessadoEnum processado) {
        this.id = id;
        this.agencia = agencia;
        this.conta = conta;
        this.saldo = saldo;
        this.status = status;
        this.dataCadastro = dataCadastro;
        this.processado = processado;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public ProcessadoEnum getProcessado() {
		return processado;
	}

	public void setProcessado(ProcessadoEnum processado) {
		this.processado = processado;
	}
}
