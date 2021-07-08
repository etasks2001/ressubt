package com.ressubt.action.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.ressubt.dao.ProdutoDao;
import com.ressubt.model.Participante;
import com.ressubt.model.Produto;

public class ProdutoQuery extends Query<Participante> {

    Object executeDaoQuery(Connection connection, Map<String, String> parameters) throws SQLException {

	List<Produto> list = new ProdutoDao().getAll(connection, parameters);

	return new Gson().toJson(list);
    }
}