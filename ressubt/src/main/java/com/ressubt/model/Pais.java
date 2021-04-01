package com.ressubt.model;

public class Pais implements Model {

    private String codigo;
    private String descricao;

    public Pais(String codigo, String descricao) {
	this.codigo = codigo;
	this.descricao = descricao;

    }

    public String getCodigo() {
	return codigo;
    }

    public String getDescricao() {
	return descricao;
    }

}
