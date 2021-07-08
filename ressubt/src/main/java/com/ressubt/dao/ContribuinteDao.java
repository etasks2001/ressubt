package com.ressubt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;

import com.ressubt.model.Contribuinte;
import com.ressubt.util.Util;

public class ContribuinteDao extends Dao<Contribuinte> {

    @Override
    public List<Contribuinte> getAll(Connection connection, Map<String, String> pagination) {
	return null;
    }

    @Override
    public Contribuinte getRegistro(Object codigo, Connection connection) {
	return null;
    }

    @Override
    public void delete(Contribuinte model, Connection connection) {

    }

    @Override
    public void update(Contribuinte model, Connection connection) {

    }

    @Override
    public void insert(Contribuinte model, Connection connection) throws SQLException {
	String SQL_INSERT = Util.RESOURCE_BUNDLE.getString(this.getClass().getSimpleName() + "_i");

	PreparedStatement ps = null;
	try {
	    checkFields(model);

	    ps = connection.prepareStatement(SQL_INSERT);
	    ps.setString(1, model.getNome());
	    ps.setString(2, model.getCnpj());
	    ps.setString(3, model.getIe());
	    ps.setString(4, model.getCod_mun());
	    ps.setString(5, model.getCod_ver());
	    ps.setString(6, model.getCod_fin());

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
    void checkFields(Contribuinte model) throws SQLException {
	if (isNullOrEmpty(model.getNome())) {
	    throw new SQLException("Nome inválido.");
	} else if (isNullOrEmpty(model.getCnpj())) {
	    throw new SQLException("CNPJ inválido.");
	} else if (isNullOrEmpty(model.getIe())) {
	    throw new SQLException("Inscrição Estadual inválida.");
	} else if (isNullOrEmpty(model.getCod_mun())) {
	    throw new SQLException("Município inválido.");
	} else if (isNullOrEmpty(model.getCod_ver())) {
	    throw new SQLException("Versão inválida.");
	} else if (isNullOrEmpty(model.getCod_fin())) {
	    throw new SQLException("Finalidade inválida.");
	}
    }
}