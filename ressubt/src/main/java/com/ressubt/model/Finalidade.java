package com.ressubt.model;

public class Finalidade {

    String codigo;
    String descricao;

    public Finalidade(String codigo, String descricao) {
	super();
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
