package com.ressubt.dao;

import java.math.BigDecimal;
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
import com.ressubt.model.Produto;
import com.ressubt.util.Util;

public class ProdutoDao extends Dao<Produto> {
    private static final Logger LOG = LogManager.getLogger(JsonFinalidade.class);

    @Override
    public List<Produto> getAll(Connection connection, Map<String, String> parameters) throws SQLException {
	String currentContribuinte = parameters.get("currentContribuinte");
	String parameter = MatchZeroOrMoreChar(parameters.get("parameter"));
	String order = parameters.get("order");
	int page = Integer.parseInt(parameters.get("page"));

	page = calcularOffSet(page);

	List<Produto> produto = new ArrayList<Produto>();
	ResultSet rs = null;
	PreparedStatement ps = null;

	String sql_search = Util.RESOURCE_BUNDLE.getString(this.getClass().getSimpleName() + "_search");

	try {
	    ps = connection.prepareStatement(String.format(sql_search, currentContribuinte, parameter, order, page));

	    rs = ps.executeQuery();

	    while (rs.next()) {
		Integer sk = rs.getInt("sk");
		Integer contribuinte = rs.getInt("contribuinte");
		String cod_item = rs.getString("cod_item");
		String descr_item = rs.getString("descr_item");
		String cod_barra = rs.getString("cod_barra");
		String unid_inv = rs.getString("unid_inv");
		String cod_ncm = rs.getString("cod_ncm");
		BigDecimal aliq_icms = rs.getBigDecimal("aliq_icms");
		String cest = rs.getString("cest");

		produto.add(new Produto(sk, contribuinte, cod_item, descr_item, cod_barra, unid_inv, cod_ncm, aliq_icms, cest));
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
	return produto;
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
	    LOG.error("SEVERE", e);
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