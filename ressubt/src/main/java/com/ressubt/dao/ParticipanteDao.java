package com.ressubt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.ressubt.model.Participante;
import com.ressubt.util.Util;

public class ParticipanteDao extends Dao<Participante> {

    @Override
    public List<Participante> getAll(Connection connection, String... parameters) {
	return null;
    }

    @Override
    public Participante getRegistro(Object codigo, Connection connection) {
	return null;
    }

    @Override
    public void delete(Participante model, Connection connection) {

    }

    @Override
    public void update(Participante model, Connection connection) {

    }

    @Override
    public void insert(Participante model, Connection connection) throws SQLException {
	String sql = Util.RESOURCE_BUNDLE.getString(this.getClass().getSimpleName() + "_i");

	PreparedStatement ps = null;
	try {
	    checkFields(model);

	    ps = connection.prepareStatement(sql);
	    ps.setInt(1, model.getContribuinte());
	    ps.setString(2, model.getNome());
	    ps.setString(3, model.getCod_pais());
	    ps.setString(4, model.getCnpj_cpf());
	    ps.setString(5, model.getIe());
	    ps.setString(6, model.getCod_mun());

	    ps.executeUpdate();
	} catch (SQLException e) {
	    throw new SQLException(e);
	} finally {
	    DbUtils.closeQuietly(ps);
	    DbUtils.closeQuietly(connection);
	    connection = null;
	}
    }

    @Override
    void checkFields(Participante model) throws SQLException {
	if (isNullOrEmpty(model.getNome())) {
	    throw new SQLException("Nome inválido.");
	} else if (isNullOrEmpty(model.getCod_pais())) {
	    throw new SQLException("País inválido.");
	} else if (isNullOrEmpty(model.getCnpj_cpf())) {
	    throw new SQLException("CNPJ/CPF inválida.");
	} else if (isNullOrEmpty(model.getIe())) {
	    throw new SQLException("Inscrição Estadual inválida.");
	} else if (isNullOrEmpty(model.getCod_mun())) {
	    throw new SQLException("Município inválido.");
	}
    }
}