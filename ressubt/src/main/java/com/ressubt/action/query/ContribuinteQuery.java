package com.ressubt.action.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.ressubt.dao.ContribuinteDao;
import com.ressubt.model.Contribuinte;
import com.ressubt.model.Participante;

public class ContribuinteQuery extends Query<Participante> {

    Object executeDaoQuery(Connection connection, Map<String, String> parameters) throws SQLException {

	List<Contribuinte> list = new ContribuinteDao().getAll(connection, parameters);

	return new Gson().toJson(list);
    }
}