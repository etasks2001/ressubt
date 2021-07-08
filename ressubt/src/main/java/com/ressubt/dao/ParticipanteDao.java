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
import com.ressubt.model.Participante;
import com.ressubt.util.Util;

public class ParticipanteDao extends Dao<Participante> {
    private static final Logger LOG = LogManager.getLogger(JsonFinalidade.class);

    @Override
    public List<Participante> getAll(Connection connection, Map<String, String> parameters) throws SQLException {
	String currentContribuinte = parameters.get("currentContribuinte");
	StringBuilder parameter = new StringBuilder();
	parameter.append('%');
	parameter.append(parameters.get("parameter"));
	parameter.append('%');

	int page = Integer.parseInt(parameters.get("page"));

	page = ((page - 1) * 30);

	List<Participante> participantes = new ArrayList<Participante>();
	ResultSet rs = null;
	PreparedStatement ps = null;

	String sql_search = Util.RESOURCE_BUNDLE.getString(this.getClass().getSimpleName() + "_search");

	try {
	    ps = connection.prepareStatement(String.format(sql_search, currentContribuinte, parameter, page));

	    rs = ps.executeQuery();

	    while (rs.next()) {
		Integer sk = rs.getInt("sk");
		Integer contribuinte = rs.getInt("contribuinte");
		String nome = rs.getString("nome");
		String cod_pais = rs.getString("cod_pais");
		String cnpj_cpf = rs.getString("cnpj_cpf");
		String ie = rs.getString("ie");
		String cod_mun = rs.getString("cod_mun");

		participantes.add(new Participante(sk, contribuinte, nome, cod_pais, cnpj_cpf, ie, cod_mun));
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
	return participantes;

    }

    @Override
    public Participante getRegistro(Object codigo, Connection connection) {
	return null;
    }

    @Override
    public void delete(Participante model, Connection connection) {

    }

    @Override
    public void update(Participante model, Connection connection) {

    }

    @Override
    public void insert(Participante model, Connection connection) throws SQLException {
	PreparedStatement ps = null;
	try {
	    checkFields(model);
	    String sql = Util.RESOURCE_BUNDLE.getString(this.getClass().getSimpleName() + "_i");

	    ps = connection.prepareStatement(sql);
	    ps.setInt(1, model.getContribuinte());
	    ps.setString(2, model.getNome());
	    ps.setString(3, model.getCod_pais());
	    ps.setString(4, model.getCnpj_cpf());
	    ps.setString(5, model.getIe());
	    ps.setString(6, model.getCod_mun());

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
    void checkFields(Participante model) throws SQLException {
	if (isNullOrEmpty(model.getNome())) {
	    throw new SQLException("Nome inválido.");
	} else if (isNullOrEmpty(model.getCod_pais())) {
	    throw new SQLException("País inválido.");
	} else if (isNullOrEmpty(model.getCnpj_cpf())) {
	    throw new SQLException("CNPJ/CPF inválida.");
	} else if (isNullOrEmpty(model.getIe())) {
	    throw new SQLException("Inscrição Estadual inválida.");
	} else if (isNullOrEmpty(model.getCod_mun())) {
	    throw new SQLException("Município inválido.");
	}
    }
}