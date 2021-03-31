package com.ressubt.action.cadastro;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ressubt.model.Contribuinte;
import com.ressubt.util.IntegerEmptyDeserializer;

public class ContribuinteCad extends Cadastro<Contribuinte> {

    void populatePreparedStatement(String model, PreparedStatement ps) throws SQLException {
	System.out.println(model);
	Gson gson = new GsonBuilder().registerTypeAdapter(Integer.class, new IntegerEmptyDeserializer()).create();
	Contribuinte contribuinte = gson.fromJson(model, Contribuinte.class);
	checkFields(contribuinte);

	ps.setString(1, contribuinte.getNome());
	ps.setString(2, contribuinte.getCnpj());
	ps.setString(3, contribuinte.getIe());
	ps.setString(4, contribuinte.getCod_mun());
	ps.setString(5, contribuinte.getCod_ver());
	ps.setString(6, contribuinte.getCod_fin());
	if (contribuinte.getSk() > 0) {
	    ps.setInt(7, contribuinte.getSk());
	}
    }

    @Override
    void checkFields(Contribuinte model) throws SQLException {
	String nome = model.getNome();
	String cnpj = model.getCnpj();
	String ie = model.getIe();
	String cod_mun = model.getCod_mun();
	String cod_ver = model.getCod_ver();
	String cod_fin = model.getCod_fin();

	String errorMessage = "";

	if (isNullOrEmpty(nome)) {
	    errorMessage = "Nome inválido.";
	} else if (isNullOrEmpty(cnpj)) {
	    errorMessage = "CNPJ inválido.";
	} else if (isNullOrEmpty(ie)) {
	    errorMessage = "Inscrição Estadual inválida.";
	} else if (isNullOrEmpty(cod_mun)) {
	    errorMessage = "Município inválido.";
	} else if (isNullOrEmpty(cod_ver)) {
	    errorMessage = "Versão inválida.";
	} else if (isNullOrEmpty(cod_fin)) {
	    errorMessage = "Finalidade inválida.";
	}

	if (campoComErro(errorMessage)) {
	    throw new SQLException(errorMessage);
	}

    }

}
