package com.ressubt.model;

public class Participante {
    private Integer sk;
    private Integer contribuinte;
    private String nome;
    private String cod_pais;
    private String cnpj_cpf;
    private String ie;
    private String uf;
    private String cod_mun;

    public Participante(Integer sk, Integer contribuinte, String nome, String cod_pais, String cnpj_cpf, String ie, String uf, String cod_mun) {
	this.sk = sk;
	this.contribuinte = contribuinte;
	this.nome = nome;
	this.cod_pais = cod_pais;
	this.cnpj_cpf = cnpj_cpf;
	this.ie = ie;
	this.uf = uf;
	this.cod_mun = cod_mun;
    }

    public String getUf() {
	return uf;
    }

    public Integer getSk() {
	return sk;
    }

    public Integer getContribuinte() {
	return contribuinte;
    }

    public String getNome() {
	return nome;
    }

    public String getCod_pais() {
	return cod_pais;
    }

    public String getCnpj_cpf() {
	return cnpj_cpf;
    }

    public String getIe() {
	return ie;
    }

    public String getCod_mun() {
	return cod_mun;
    }

}
