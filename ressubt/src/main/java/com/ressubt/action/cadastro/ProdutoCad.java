package com.ressubt.action.cadastro;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ressubt.model.Produto;
import com.ressubt.util.IntegerEmptyDeserializer;

public class ProdutoCad extends Cadastro {

    void populatePreparedStatement(String model, PreparedStatement ps) throws SQLException {
	System.out.println(model);

	Gson gson = new GsonBuilder().registerTypeAdapter(Integer.class, new IntegerEmptyDeserializer()).create();

	Produto produto = gson.fromJson(model, Produto.class);

	ps.setInt(1, produto.getContribuinte());
	ps.setString(2, produto.getCod_item());
	ps.setString(3, produto.getDescr_item());
	ps.setString(4, produto.getCod_barra());
	ps.setString(5, produto.getUnid_inv());
	ps.setString(6, produto.getCod_ncm());
	ps.setBigDecimal(7, produto.getAliq_icms());
	ps.setString(8, produto.getCest());
	if (produto.getSk() > 0) {
	    ps.setInt(9, produto.getSk());
	}

    }
}
