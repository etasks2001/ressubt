package com.ressubt.model;

public class Contribuinte {

    private Integer sk;

    private String nome;
    private String cnpj;

    private String ie;

    private String cod_mun;
    private String cod_ver;
    private String cod_fin;

    public Contribuinte(Integer sk, String nome, String cnpj, String ie, String cod_mun, String cod_ver, String cod_fin) {
	super();
	this.sk = sk;
	this.nome = nome;
	this.cnpj = cnpj;
	this.ie = ie;
	this.cod_mun = cod_mun;
	this.cod_ver = cod_ver;
	this.cod_fin = cod_fin;
    }

    public Integer getSk() {
	return sk;
    }

    public String getNome() {
	return nome;
    }

    public String getCnpj() {
	return cnpj;
    }

    public String getIe() {
	return ie;
    }

    public String getCod_mun() {
	return cod_mun;
    }

    public String getCod_ver() {
	return cod_ver;
    }

    public String getCod_fin() {
	return cod_fin;
    }

}
