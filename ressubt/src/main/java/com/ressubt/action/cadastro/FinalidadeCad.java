package com.ressubt.action.cadastro;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.ressubt.model.Finalidade;

public class FinalidadeCad extends Cadastro<Finalidade> {

    void populatePreparedStatement(String model, PreparedStatement ps) throws SQLException {
	System.out.println(model);

	Finalidade finalidade = new Gson().fromJson(model, Finalidade.class);
	checkFields(finalidade);
	ps.setString(1, finalidade.getDescricao());
	ps.setString(2, finalidade.getCodigo());
    }

    @Override
    void checkFields(Finalidade model) throws SQLException {
	String codigo = model.getCodigo();
	String descricao = model.getDescricao();
	String errorMessage = "";

	if (isNullOrEmpty(codigo)) {
	    errorMessage = "Código inválido.";
	} else if (isNullOrEmpty(descricao)) {
	    errorMessage = "Descrição inválida.";
	}

	if (campoComErro(errorMessage)) {
	    throw new SQLException(errorMessage);
	}
    }
}