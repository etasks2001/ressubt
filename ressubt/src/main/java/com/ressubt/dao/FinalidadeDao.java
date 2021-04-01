package com.ressubt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.ressubt.model.Finalidade;
import com.ressubt.util.Util;

public class FinalidadeDao extends Dao<Finalidade, String> {

    public FinalidadeDao(Connection connection) {
	super(connection);
    }

    @Override
    public List<Finalidade> getAll() {

	return null;
    }

    @Override
    public Finalidade getRegistro(String codigo) {

	return null;
    }

    @Override
    public void delete(Finalidade model) {

    }

    @Override
    public String update(Finalidade model) {
	return "";

    }

    @Override
    public String insert(Finalidade model) {
	String sql = Util.RESOURCE_BUNDLE.getString(this.getClass().getSimpleName() + "_i");

	PreparedStatement ps = null;
	try {

	    ps = connection.prepareStatement(sql);

	    checkFields(model);

	    ps.setString(1, model.getDescricao());
	    ps.setString(2, model.getCodigo());

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
