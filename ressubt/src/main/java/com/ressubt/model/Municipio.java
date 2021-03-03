package com.ressubt.model;

public class Municipio {

    private int codigo;
    private String descricao;

    public Municipio(int codigo, String descricao) {
	this.codigo = codigo;
	// TODO Auto-generated constructor stub
	this.descricao = descricao;
    }

    public int getCodigo() {
	return codigo;
    }

    public String getDescricao() {
	return descricao;
    }

}
