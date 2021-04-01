package com.ressubt.action.cadastro;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ressubt.dao.Dao;
import com.ressubt.dao.ParticipanteDao;
import com.ressubt.model.Participante;
import com.ressubt.util.BigDecimalEmptyDeserializer;
import com.ressubt.util.IntegerEmptyDeserializer;

public class ParticipanteCad extends Cadastro<Participante> {// implements Action {

    String executeDao(String json) throws SQLException {
	System.out.println(json);

	Gson gson = new GsonBuilder().registerTypeAdapter(BigDecimal.class, new BigDecimalEmptyDeserializer()).registerTypeAdapter(Integer.class, new IntegerEmptyDeserializer()).create();
	Participante model = gson.fromJson(json, Participante.class);

	Dao<Participante, Integer> dao = new ParticipanteDao(conn);

	if (operation.equals("i")) {
	    return dao.insert(model);
	} else if (operation.equals("u")) {
	    return dao.update(model);
	}

	return "";
    }
}