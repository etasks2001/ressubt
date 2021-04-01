package com.ressubt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.ressubt.model.Contribuinte;
import com.ressubt.util.Util;

public class ContribuinteDao extends Dao<Contribuinte> {

    @Override
    public List<Contribuinte> getAll(Connection connection) {

	return null;
    }

    @Override
    public Contribuinte getRegistro(Object codigo, Connection connection) {

	return null;
    }

    @Override
    public String insert(Contribuinte model, Connection connection) {
	String sql = Util.RESOURCE_BUNDLE.getString(this.getClass().getSimpleName() + "_i");
	String responseMessage = null;
	PreparedStatement ps = null;
	try {

	    ps = connection.prepareStatement(sql);

	    checkFields(model);

	    ps.setString(1, model.getNome());
	    ps.setString(2, model.getCnpj());
	    ps.setString(3, model.getIe());
	    ps.setString(4, model.getCod_mun());
	    ps.setString(5, model.getCod_ver());
	    ps.setString(6, model.getCod_fin());

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
    public void delete(Contribuinte model, Connection connection) {

    }

    @Override
    public String update(Contribuinte model, Connection connection) {
	return "";
    }

    @Override
    void checkFields(Contribuinte model) throws SQLException {
	String nome = model.getNome();
	String cnpj = model.getCnpj();
	String ie = model.getIe();
	String cod_mun = model.getCod_mun();
	String cod_ver = model.getCod_ver();
	String cod_fin = model.getCod_fin();

	String errorMessage = "";

	if (isNullOrEmpty(nome)) {
	    errorMessage = "Nome inválido.";
	} else if (isNullOrEmpty(cnpj)) {
	    errorMessage = "CNPJ inválido.";
	} else if (isNullOrEmpty(ie)) {
	    errorMessage = "Inscrição Estadual inválida.";
	} else if (isNullOrEmpty(cod_mun)) {
	    errorMessage = "Município inválido.";
	} else if (isNullOrEmpty(cod_ver)) {
	    errorMessage = "Versão inválida.";
	} else if (isNullOrEmpty(cod_fin)) {
	    errorMessage = "Finalidade inválida.";
	}

	if (campoComErro(errorMessage)) {
	    throw new SQLException(errorMessage);
	}
    }
}