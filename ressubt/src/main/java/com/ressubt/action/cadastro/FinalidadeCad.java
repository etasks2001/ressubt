package com.ressubt.action.cadastro;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ressubt.dao.Dao;
import com.ressubt.dao.FinalidadeDao;
import com.ressubt.model.Finalidade;
import com.ressubt.util.BigDecimalEmptyDeserializer;
import com.ressubt.util.IntegerEmptyDeserializer;

public class FinalidadeCad extends Cadastro<Finalidade> {

    String executeDao(String json) throws SQLException {
	System.out.println(json);

	Gson gson = new GsonBuilder().registerTypeAdapter(BigDecimal.class, new BigDecimalEmptyDeserializer()).registerTypeAdapter(Integer.class, new IntegerEmptyDeserializer()).create();

	Finalidade model = gson.fromJson(json, Finalidade.class);

	Dao<Finalidade, String> dao = new FinalidadeDao(conn);

	if (operation.equals("i")) {
	    return dao.insert(model);
	} else if (operation.equals("u")) {
	    return dao.update(model);
	}

	return "";
    }
}