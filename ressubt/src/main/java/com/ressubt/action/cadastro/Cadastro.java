package com.ressubt.action.cadastro;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ressubt.action.Action;
import com.ressubt.control.FlowEmpty;
import com.ressubt.control.HttpFlow;
import com.ressubt.model.Model;
import com.ressubt.util.Util;

public abstract class Cadastro<T extends Model> implements Action {

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	String model = request.getParameter("model");
	String operation = request.getParameter("operation");
	String sql = Util.RESOURCE_BUNDLE.getString(this.getClass().getSimpleName() + "_" + operation);
	ComboPooledDataSource dataSource = (ComboPooledDataSource) request.getServletContext().getAttribute("dataSource");

	Connection conn = null;
	PreparedStatement ps = null;
	String responseMessage = null;
	PrintWriter writer = null;

	response.setStatus(200);
	response.setHeader("Content-Type", "application/json");
	response.setCharacterEncoding("UTF-8");

	try {
	    writer = response.getWriter();

	    conn = dataSource.getConnection();
	    ps = conn.prepareStatement(sql);

	    populatePreparedStatement(model, ps);

	    int total = ps.executeUpdate();

	    if (total > 0) {
		responseMessage = Util.createResponseMessage(0, "gravado com sucesso.");
	    } else {
		responseMessage = Util.createResponseMessage("erro gravação.");
	    }
	} catch (SQLException | IOException e) {
	    responseMessage = Util.createResponseMessage(e.getMessage());
	} finally {
	    DbUtils.closeQuietly(ps);
	    DbUtils.closeQuietly(conn);
	    conn = null;
	    writer.print(responseMessage);
	}

	return new FlowEmpty("");
    }

    abstract void populatePreparedStatement(String model, PreparedStatement ps) throws SQLException;

    abstract void checkFields(T model) throws SQLException;

    boolean isNullOrEmpty(String field) {
	return field == null || field.length() == 0;
    }

    boolean isNullOrEmpty(BigDecimal field) {
	return field == null || field.doubleValue() == 0;
    }

    boolean campoComErro(String errorMessage) {
	return errorMessage.length() > 0;
    }

    static boolean isValidField(String field, String regex) {
	return field.matches(regex);
    }

    public static String REGEX_CODIGO_MUNICIPIO = "[0-9]{7}";
    public static String REGEX_CODIGO_VERSAO = "[0-9]{2}|[0-9]{1}";
    public static String REGEX_CNPJ = "[0-9]{2}[\\\\.]?[0-9]{3}[\\\\.]?[0-9]{3}[\\\\/]?[0-9]{4}[-]?[0-9]{2}";
    public static String REGEX_CNPJ_CPF = "([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})\r\n" + "";

    public static void main(String[] args) {
	System.out.println(Cadastro.isValidField("0000000", REGEX_CODIGO_MUNICIPIO));
	System.out.println(Cadastro.isValidField("00", REGEX_CODIGO_VERSAO));
	System.out.println(Cadastro.isValidField("0", REGEX_CODIGO_VERSAO));
	System.out.println(Cadastro.isValidField("66.666.666/1111-11", REGEX_CNPJ));

    }

}
