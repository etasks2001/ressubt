package com.ressubt.action.cadastro;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ressubt.model.Contribuinte;
import com.ressubt.util.IntegerEmptyDeserializer;

public class ContribuinteCad extends Cadastro {// implements Action {

    void populatePreparedStatement(String model, PreparedStatement ps) throws SQLException {
	System.out.println(model);
	Gson gson = new GsonBuilder().registerTypeAdapter(Integer.class, new IntegerEmptyDeserializer()).create();
	Contribuinte contribuinte = gson.fromJson(model, Contribuinte.class);

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

}
