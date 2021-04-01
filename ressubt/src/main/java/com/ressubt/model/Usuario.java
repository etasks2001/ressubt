package com.ressubt.model;

public class Usuario implements Model {

    private String email;
    private String senha;

    public Usuario(String email, String senha) {
	super();
	this.email = email;
	this.senha = senha;
    }

    public String getEmail() {
	return email;
    }

    public String getSenha() {
	return senha;
    }

}
