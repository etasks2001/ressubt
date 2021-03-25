package com.ressubt.action.cadastro;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.ressubt.model.Finalidade;

public class FinalidadeCad extends Cadastro {// implements Action {

    void populatePreparedStatement(String model, PreparedStatement ps) throws SQLException {
	System.out.println(model);
	Finalidade finalidade = new Gson().fromJson(model, Finalidade.class);

	ps.setString(1, finalidade.getDescricao());
	ps.setString(2, finalidade.getCodigo());
    }
}