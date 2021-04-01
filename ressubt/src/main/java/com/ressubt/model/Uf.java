package com.ressubt.model;

public class Uf implements Model {

    private int codigo;
    private String descricao;

    public Uf(int codigo, String descricao) {
	this.codigo = codigo;
	this.descricao = descricao;
    }

    public int getCodigo() {
	return codigo;
    }

    public String getDescricao() {
	return descricao;
    }

}
