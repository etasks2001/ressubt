package com.ressubt.action.cadastro;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ressubt.dao.Dao;
import com.ressubt.dao.ProdutoDao;
import com.ressubt.model.Produto;
import com.ressubt.util.BigDecimalEmptyDeserializer;
import com.ressubt.util.IntegerEmptyDeserializer;

public class ProdutoCad extends Cadastro<Produto> {

    String executeDao(String json) throws SQLException {
	System.out.println(json);

	Gson gson = new GsonBuilder().registerTypeAdapter(BigDecimal.class, new BigDecimalEmptyDeserializer()).registerTypeAdapter(Integer.class, new IntegerEmptyDeserializer()).create();
	Produto model = gson.fromJson(json, Produto.class);

	Dao<Produto, Integer> dao = new ProdutoDao(conn);

	if (operation.equals("i")) {
	    return dao.insert(model);
	} else if (operation.equals("u")) {
	    return dao.update(model);
	}

	return "";
    }
}