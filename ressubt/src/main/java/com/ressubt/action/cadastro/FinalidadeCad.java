package com.ressubt.action.cadastro;

import java.sql.Connection;
import java.sql.SQLException;

import com.ressubt.dao.FinalidadeDao;
import com.ressubt.model.Finalidade;

public class FinalidadeCad extends Cadastro<Finalidade> {

    void executeDao(String json, Connection connection, String operation) throws SQLException {
	System.out.println(json);

	Finalidade model = fromJson(json, Finalidade.class);
	FinalidadeDao dao = new FinalidadeDao();

	if (operation.equals("i")) {
	    dao.insert(model, connection);
	} else if (operation.equals("u")) {
	    dao.update(model, connection);
	}

    }
}