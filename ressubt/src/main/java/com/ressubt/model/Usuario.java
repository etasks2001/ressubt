package com.ressubt.model;

public class Usuario implements Model {

    private String email;
    private String senha;
    private String currentContribuinte;

    public Usuario(String email, String senha, String currentContribuinte) {
	this.email = email;
	this.senha = senha;
	this.currentContribuinte = currentContribuinte;
    }

    public String getEmail() {
	return email;
    }

    public String getSenha() {
	return senha;
    }

    public String getCurrentContribuinte() {
	return currentContribuinte;
    }

}
