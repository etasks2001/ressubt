package com.ressubt.action.cadastro;

import java.sql.Connection;
import java.sql.SQLException;

import com.ressubt.dao.ContribuinteDao;
import com.ressubt.model.Contribuinte;

public class ContribuinteCad extends Cadastro<Contribuinte> {

    String executeDao(String json, Connection connection, String operation) throws SQLException {
	System.out.println(json);

	Contribuinte model = fromJson(json, Contribuinte.class);
	ContribuinteDao dao = new ContribuinteDao();

	if (operation.equals("i")) {
	    return dao.insert(model, connection);
	} else if (operation.equals("u")) {
	    return dao.update(model, connection);
	}

	return "";
    }
}