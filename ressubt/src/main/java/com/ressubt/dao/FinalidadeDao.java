package com.ressubt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ressubt.action.JsonFinalidade;
import com.ressubt.model.Finalidade;
import com.ressubt.util.Util;

public class FinalidadeDao extends Dao<Finalidade> {
    private static final Logger LOG = LogManager.getLogger(JsonFinalidade.class);

    @Override
    public List<Finalidade> getAll(Connection connection, Map<String, String> parameters) throws SQLException {

	StringBuilder parameter = new StringBuilder();
	parameter.append('%');
	parameter.append(parameters.get("parameter"));
	parameter.append('%');

	int page = Integer.parseInt(parameters.get("page"));

	page = ((page - 1) * 30);

	List<Finalidade> finalidade = new ArrayList<Finalidade>();
	ResultSet rs = null;
	PreparedStatement ps = null;

	String sql_search = Util.RESOURCE_BUNDLE.getString(this.getClass().getSimpleName() + "_search");

	try {
	    ps = connection.prepareStatement(String.format(sql_search, parameter, page));

	    rs = ps.executeQuery();

	    while (rs.next()) {
		String codigo = rs.getString("codigo");
		String descricao = rs.getString("descricao");

		finalidade.add(new Finalidade(codigo, descricao));
	    }
	} catch (SQLException e) {
	    LOG.error("SEVERE", e);
	    throw new SQLException(e);
	} finally {
	    DbUtils.closeQuietly(ps);
	    DbUtils.closeQuietly(rs);
	    DbUtils.closeQuietly(connection);
	    connection = null;
	}
	return finalidade;

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
	    LOG.error("SEVERE", e);
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