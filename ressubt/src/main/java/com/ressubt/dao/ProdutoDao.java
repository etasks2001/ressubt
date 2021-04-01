package com.ressubt.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.ressubt.model.Produto;
import com.ressubt.util.Util;

public class ProdutoDao extends Dao<Produto, Integer> {

    public ProdutoDao(Connection connection) {
	super(connection);
    }

    @Override
    public List<Produto> getAll() {

	return null;
    }

    @Override
    public Produto getRegistro(Integer codigo) {

	return null;
    }

    @Override
    public void delete(Produto model) {

    }

    @Override
    public String update(Produto model) {
	return "";
    }

    @Override
    public String insert(Produto model) {
	String sql = Util.RESOURCE_BUNDLE.getString(this.getClass().getSimpleName() + "_i");

	PreparedStatement ps = null;
	try {

	    ps = connection.prepareStatement(sql);

	    checkFields(model);

	    ps.setInt(1, model.getContribuinte());
	    ps.setString(2, model.getCod_item());
	    ps.setString(3, model.getDescr_item());
	    ps.setString(4, model.getCod_barra());
	    ps.setString(5, model.getUnid_inv());
	    ps.setString(6, model.getCod_ncm());
	    ps.setBigDecimal(7, model.getAliq_icms());
	    ps.setString(8, model.getCest());

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
    void checkFields(Produto model) throws SQLException {

	String cod_item = model.getCod_item();
	String descr_item = model.getDescr_item();
	String cod_barra = model.getCod_barra();
	String unid_inv = model.getUnid_inv();
	String cod_ncm = model.getCod_ncm();
	BigDecimal aliq_icms = model.getAliq_icms();
	String cest = model.getCest();

	String errorMessage = "";

	if (isNullOrEmpty(cod_item)) {
	    errorMessage = "Código inválido.";
	} else if (isNullOrEmpty(descr_item)) {
	    errorMessage = "Descrição inválida.";
	} else if (isNullOrEmpty(cod_barra)) {
	    errorMessage = "Código de barras inválido.";
	} else if (isNullOrEmpty(unid_inv)) {
	    errorMessage = "Unidade inválida.";
	} else if (isNullOrEmpty(cod_ncm)) {
	    errorMessage = "NCM inválida.";
	} else if (isNullOrEmpty(aliq_icms)) {
	    errorMessage = "Alíquota ICMS inválida.";
	} else if (isNullOrEmpty(cest)) {
	    errorMessage = "CEST inválida.";
	}

	if (campoComErro(errorMessage)) {
	    throw new SQLException(errorMessage);
	}
    }

}
