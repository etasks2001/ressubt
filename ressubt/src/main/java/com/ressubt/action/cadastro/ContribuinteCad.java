package com.ressubt.action.cadastro;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ressubt.dao.ContribuinteDao;
import com.ressubt.dao.Dao;
import com.ressubt.model.Contribuinte;
import com.ressubt.util.BigDecimalEmptyDeserializer;
import com.ressubt.util.IntegerEmptyDeserializer;

public class ContribuinteCad extends Cadastro<Contribuinte> {

    String executeDao(String json) throws SQLException {
	System.out.println(json);

	Gson gson = new GsonBuilder().registerTypeAdapter(BigDecimal.class, new BigDecimalEmptyDeserializer()).registerTypeAdapter(Integer.class, new IntegerEmptyDeserializer()).create();

	Contribuinte model = gson.fromJson(json, Contribuinte.class);

	Dao<Contribuinte, Integer> dao = new ContribuinteDao(conn);

	if (operation.equals("i")) {
	    return dao.insert(model);
	} else if (operation.equals("u")) {
	    return dao.update(model);
	}

	return "";
    }
}