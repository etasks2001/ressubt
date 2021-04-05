package com.ressubt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.ressubt.model.Finalidade;
import com.ressubt.util.Util;

public class FinalidadeDao extends Dao<Finalidade> {

    @Override
    public List<Finalidade> getAll(Connection connection) {
	return null;
    }

    @Override
    public Finalidade getRegistro(Object codigo, Connection connection) {
	return null;
    }

    @Override
    public void delete(Finalidade model, Connection connection) {

    }

    @Override
    public void update(Finalidade model, Connection connection) {

    }

    @Override
    public void insert(Finalidade model, Connection connection) throws SQLException {
	String sql = Util.RESOURCE_BUNDLE.getString(this.getClass().getSimpleName() + "_i");

	PreparedStatement ps = null;
	try {
	    checkFields(model);

	    ps = connection.prepareStatement(sql);
	    ps.setString(1, model.getDescricao());
	    ps.setString(2, model.getCodigo());

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
    void checkFields(Finalidade model) throws SQLException {
	if (isNullOrEmpty(model.getCodigo())) {
	    throw new SQLException("Código inválido.");
	} else if (isNullOrEmpty(model.getDescricao())) {
	    throw new SQLException("Descrição inválida.");
	}
    }
}