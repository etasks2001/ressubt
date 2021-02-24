package com.ressubt.model;

import java.math.BigDecimal;

public class Saldo {
    private Integer produto;
    private Integer ano;
    private Integer mes;
    private Integer qtd_ini;
    private BigDecimal icms_tot_ini_st;
    private BigDecimal icms_tot_ini_proprio;
    private Integer qtd_fim;
    private BigDecimal icms_tot_fim_st;
    private BigDecimal icms_tot_fim_proprio;
    private String fixo;
    private Integer entrada;
    private Integer saida;

    public Saldo(Integer produto, Integer ano, Integer mes, Integer qtd_ini, BigDecimal icms_tot_ini_st, BigDecimal icms_tot_ini_proprio, Integer qtd_fim, BigDecimal icms_tot_fim_st,
	    BigDecimal icms_tot_fim_proprio, String fixo, Integer entrada, Integer saida) {
	this.produto = produto;
	this.ano = ano;
	this.mes = mes;
	this.qtd_ini = qtd_ini;
	this.icms_tot_ini_st = icms_tot_ini_st;
	this.icms_tot_ini_proprio = icms_tot_ini_proprio;
	this.qtd_fim = qtd_fim;
	this.icms_tot_fim_st = icms_tot_fim_st;
	this.icms_tot_fim_proprio = icms_tot_fim_proprio;
	this.fixo = fixo;
	this.entrada = entrada;
	this.saida = saida;
    }

    public Integer getProduto() {
	return produto;
    }

    public Integer getAno() {
	return ano;
    }

    public Integer getMes() {
	return mes;
    }

    public Integer getQtd_ini() {
	return qtd_ini;
    }

    public BigDecimal getIcms_tot_ini_st() {
	return icms_tot_ini_st;
    }

    public BigDecimal getIcms_tot_ini_proprio() {
	return icms_tot_ini_proprio;
    }

    public Integer getQtd_fim() {
	return qtd_fim;
    }

    public BigDecimal getIcms_tot_fim_st() {
	return icms_tot_fim_st;
    }

    public BigDecimal getIcms_tot_fim_proprio() {
	return icms_tot_fim_proprio;
    }

    public String getFixo() {
	return fixo;
    }

    public Integer getEntrada() {
	return entrada;
    }

    public Integer getSaida() {
	return saida;
    }

}
