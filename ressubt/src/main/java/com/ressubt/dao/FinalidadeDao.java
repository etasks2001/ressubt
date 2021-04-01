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
    public String update(Finalidade model, Connection connection) {
	return "";

    }

    @Override
    public String insert(Finalidade model, Connection connection) throws SQLException {
	String sql = Util.RESOURCE_BUNDLE.getString(this.getClass().getSimpleName() + "_i");

	PreparedStatement ps = null;
	try {

	    ps = connection.prepareStatement(sql);

	    checkFields(model);

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
	return "";
    }

    @Override
    void checkFields(Finalidade model) throws SQLException {
	String codigo = model.getCodigo();
	String descricao = model.getDescricao();
	String errorMessage = "";

	if (isNullOrEmpty(codigo)) {
	    errorMessage = "Código inválido.";
	} else if (isNullOrEmpty(descricao)) {
	    errorMessage = "Descrição inválida.";
	}

	if (campoComErro(errorMessage)) {
	    throw new SQLException(errorMessage);
	}
    }
}