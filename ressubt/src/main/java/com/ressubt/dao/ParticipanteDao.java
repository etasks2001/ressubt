package com.ressubt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.ressubt.model.Participante;
import com.ressubt.util.Util;

public class ParticipanteDao extends Dao<Participante, Integer> {

    public ParticipanteDao(Connection connection) {
	super(connection);
    }

    @Override
    public List<Participante> getAll() {

	return null;
    }

    @Override
    public Participante getRegistro(Integer codigo) {

	return null;
    }

    @Override
    public void delete(Participante model) {

    }

    @Override
    public String update(Participante model) {
	return "";
    }

    @Override
    public String insert(Participante model) {
	String sql = Util.RESOURCE_BUNDLE.getString(this.getClass().getSimpleName() + "_i");

	PreparedStatement ps = null;
	try {

	    ps = connection.prepareStatement(sql);

	    checkFields(model);

	    ps.setInt(1, model.getContribuinte());
	    ps.setString(2, model.getNome());
	    ps.setString(3, model.getCod_pais());
	    ps.setString(4, model.getCnpj_cpf());
	    ps.setString(5, model.getIe());
	    ps.setString(6, model.getCod_mun());

	    int total = ps.executeUpdate();

	    responseMessage = checkGravacao(total);

	} catch (SQLException e) {
	    responseMessage = Util.createResponseMessage(e.getMessage());
	} finally {
	    DbUtils.closeQuietly(ps);
	    DbUtils.closeQuietly(connection);
	    connection = null;
	}
	return responseMessage;
    }

    @Override
    void checkFields(Participante model) throws SQLException {
	String nome = model.getNome();
	String cod_pais = model.getCod_pais();
	String cnpj_cpf = model.getCnpj_cpf();
	String ie = model.getIe();
	String cod_mun = model.getCod_mun();

	String errorMessage = "";

	if (isNullOrEmpty(nome)) {
	    errorMessage = "Nome inválido.";
	} else if (isNullOrEmpty(cod_pais)) {
	    errorMessage = "País inválido.";
	} else if (isNullOrEmpty(cnpj_cpf)) {
	    errorMessage = "CNPJ/CPF inválida.";
	} else if (isNullOrEmpty(ie)) {
	    errorMessage = "Inscrição Estadual inválida.";
	} else if (isNullOrEmpty(cod_mun)) {
	    errorMessage = "Município inválido.";
	}

	if (campoComErro(errorMessage)) {
	    throw new SQLException(errorMessage);
	}

    }

}
