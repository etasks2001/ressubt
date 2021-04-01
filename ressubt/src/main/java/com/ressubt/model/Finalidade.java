package com.ressubt.model;

public class Finalidade implements Model {

    String codigo;
    String descricao;

    public Finalidade(String codigo, String descricao) {
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
