package com.ressubt.action.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.ressubt.dao.FinalidadeDao;
import com.ressubt.model.Finalidade;
import com.ressubt.model.Participante;

public class FinalidadeQuery extends Query<Participante> {

    Object executeDaoQuery(Connection connection, Map<String, String> parameters) throws SQLException {

	List<Finalidade> list = new FinalidadeDao().getAll(connection, parameters);

	return new Gson().toJson(list);
    }
}