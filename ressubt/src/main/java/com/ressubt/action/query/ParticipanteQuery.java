package com.ressubt.action.query;

import java.sql.Connection;
import java.sql.SQLException;

import com.ressubt.dao.ParticipanteDao;
import com.ressubt.model.Participante;

public class ParticipanteQuery extends Query<Participante> {

    void executeDaoQuery(Connection connection, String... jsonParameters) throws SQLException {
	System.out.println(jsonParameters);

	// Participante model = fromJson(jsonParameters, Participante.class);

	ParticipanteDao dao = new ParticipanteDao();

	dao.getAll(connection, jsonParameters);

    }
}