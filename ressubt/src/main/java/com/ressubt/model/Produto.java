package com.ressubt.model;

import java.math.BigDecimal;

public class Produto implements Model {
    private Integer sk;
    private Integer contribuinte;
    private String cod_item;
    private String descr_item;
    private String cod_barra;
    private String unid_inv;
    private String cod_ncm;
    private BigDecimal aliq_icms;
    private String cest;

    public Produto(Integer sk, Integer contribuinte, String cod_item, String descr_item, String cod_barra, String unid_inv, String cod_ncm, BigDecimal aliq_icms, String cest) {
	this.sk = sk;
	this.contribuinte = contribuinte;
	this.cod_item = cod_item;
	this.descr_item = descr_item;
	this.cod_barra = cod_barra;
	this.unid_inv = unid_inv;
	this.cod_ncm = cod_ncm;
	this.aliq_icms = aliq_icms;
	this.cest = cest;
    }

    public Integer getSk() {
	return sk;
    }

    public Integer getContribuinte() {
	return contribuinte;
    }

    public String getCod_item() {
	return cod_item;
    }

    public String getDescr_item() {
	return descr_item;
    }

    public String getCod_barra() {
	return cod_barra;
    }

    public String getUnid_inv() {
	return unid_inv;
    }

    public String getCod_ncm() {
	return cod_ncm;
    }

    public BigDecimal getAliq_icms() {
	return aliq_icms;
    }

    public String getCest() {
	return cest;
    }

}
