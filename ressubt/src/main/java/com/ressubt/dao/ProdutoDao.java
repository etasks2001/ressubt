package com.ressubt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.ressubt.model.Produto;
import com.ressubt.util.Util;

public class ProdutoDao extends Dao<Produto> {

    @Override
    public List<Produto> getAll(Connection connection, String... pagination) {
	return null;
    }

    @Override
    public Produto getRegistro(Object codigo, Connection connection) {
	return null;
    }

    @Override
    public void delete(Produto model, Connection connection) {

    }

    @Override
    public void update(Produto model, Connection connection) {

    }

    @Override
    public void insert(Produto model, Connection connection) throws SQLException {
	String sql = Util.RESOURCE_BUNDLE.getString(this.getClass().getSimpleName() + "_i");

	PreparedStatement ps = null;
	try {
	    checkFields(model);

	    ps = connection.prepareStatement(sql);
	    ps.setInt(1, model.getContribuinte());
	    ps.setString(2, model.getCod_item());
	    ps.setString(3, model.getDescr_item());
	    ps.setString(4, model.getCod_barra());
	    ps.setString(5, model.getUnid_inv());
	    ps.setString(6, model.getCod_ncm());
	    ps.setBigDecimal(7, model.getAliq_icms());
	    ps.setString(8, model.getCest());

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
    void checkFields(Produto model) throws SQLException {
	if (isNullOrEmpty(model.getCod_item())) {
	    throw new SQLException("Código inválido.");
	} else if (isNullOrEmpty(model.getDescr_item())) {
	    throw new SQLException("Descrição inválida.");
	} else if (isNullOrEmpty(model.getCod_barra())) {
	    throw new SQLException("Código de barras inválido.");
	} else if (isNullOrEmpty(model.getUnid_inv())) {
	    throw new SQLException("Unidade inválida.");
	} else if (isNullOrEmpty(model.getCod_ncm())) {
	    throw new SQLException("NCM inválida.");
	} else if (isNullOrEmpty(model.getAliq_icms())) {
	    throw new SQLException("Alíquota ICMS inválida.");
	} else if (isNullOrEmpty(model.getCest())) {
	    throw new SQLException("CEST inválida.");
	}
    }
}