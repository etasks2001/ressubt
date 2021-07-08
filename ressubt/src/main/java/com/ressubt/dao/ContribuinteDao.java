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
import com.ressubt.model.Contribuinte;
import com.ressubt.util.Util;

public class ContribuinteDao extends Dao<Contribuinte> {
    private static final Logger LOG = LogManager.getLogger(JsonFinalidade.class);

    @Override
    public List<Contribuinte> getAll(Connection connection, Map<String, String> parameters) throws SQLException {
	StringBuilder parameter = new StringBuilder();
	parameter.append('%');
	parameter.append(parameters.get("parameter"));
	parameter.append('%');

	int page = Integer.parseInt(parameters.get("page"));

	page = ((page - 1) * 30);

	List<Contribuinte> contribuinte = new ArrayList<Contribuinte>();
	ResultSet rs = null;
	PreparedStatement ps = null;

	String sql_search = Util.RESOURCE_BUNDLE.getString(this.getClass().getSimpleName() + "_search");

	try {
	    ps = connection.prepareStatement(String.format(sql_search, parameter, page));

	    rs = ps.executeQuery();

	    while (rs.next()) {

		Integer sk = rs.getInt("sk");
		String nome = rs.getString("nome");
		String cnpj = rs.getString("cnpj");
		String ie = rs.getString("ie");
		String cod_mun = rs.getString("cod_mun");
		String cod_ver = rs.getString("cod_ver");
		String cod_fin = rs.getString("cod_fin");

		contribuinte.add(new Contribuinte(sk, nome, cnpj, ie, cod_mun, cod_ver, cod_fin));
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
	return contribuinte;
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
	    LOG.error("SEVERE", e);
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