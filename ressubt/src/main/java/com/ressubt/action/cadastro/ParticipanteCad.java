package com.ressubt.action.cadastro;

import java.sql.Connection;
import java.sql.SQLException;

import com.ressubt.dao.ParticipanteDao;
import com.ressubt.model.Participante;

public class ParticipanteCad extends Cadastro<Participante> {

    String executeDao(String json, Connection connection, String operation) throws SQLException {
	System.out.println(json);

	Participante model = fromJson(json, Participante.class);
	ParticipanteDao dao = new ParticipanteDao();

	if (operation.equals("i")) {
	    return dao.insert(model, connection);
	} else if (operation.equals("u")) {
	    return dao.update(model, connection);
	}

	return "";
    }
}