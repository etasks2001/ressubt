package com.ressubt.action.cadastro;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ressubt.model.Participante;
import com.ressubt.util.IntegerEmptyDeserializer;

public class ParticipanteCad extends Cadastro<Participante> {// implements Action {

    void populatePreparedStatement(String model, PreparedStatement ps) throws SQLException {
	System.out.println(model);
	Gson gson = new GsonBuilder().registerTypeAdapter(Integer.class, new IntegerEmptyDeserializer()).create();
	Participante participante = gson.fromJson(model, Participante.class);
	checkFields(participante);

	ps.setInt(1, participante.getContribuinte());
	ps.setString(2, participante.getNome());
	ps.setString(3, participante.getCod_pais());
	ps.setString(4, participante.getCnpj_cpf());
	ps.setString(5, participante.getIe());
	ps.setString(6, participante.getCod_mun());

	if (participante.getSk() > 0) {
	    ps.setInt(7, participante.getSk());
	}

    }

    @Override
    void checkFields(Participante model) throws SQLException {
	String nome = model.getNome();
	String cod_pais = model.getCod_pais();
	String cnpj_cpf = model.getCnpj_cpf();
	String ie = model.getIe();
	String cod_mun = model.getCod_mun();

	String errorMessage = "";

	if (isNullOrEmpty(nome)) {
	    errorMessage = "Nome inválido.";
	} else if (isNullOrEmpty(cod_pais)) {
	    errorMessage = "País inválido.";
	} else if (isNullOrEmpty(cnpj_cpf)) {
	    errorMessage = "CNPJ/CPF inválida.";
	} else if (isNullOrEmpty(ie)) {
	    errorMessage = "Inscrição Estadual inválida.";
	} else if (isNullOrEmpty(cod_mun)) {
	    errorMessage = "Município inválido.";
	}

	if (campoComErro(errorMessage)) {
	    throw new SQLException(errorMessage);
	}

    }

}
