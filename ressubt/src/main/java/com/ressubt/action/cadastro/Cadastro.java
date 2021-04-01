package com.ressubt.action.cadastro;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ressubt.action.Action;
import com.ressubt.control.FlowEmpty;
import com.ressubt.control.HttpFlow;
import com.ressubt.model.Model;
import com.ressubt.util.Util;

public abstract class Cadastro<T extends Model> implements Action {
    Connection conn = null;
    String operation = null;

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	String model = request.getParameter("model");
	operation = request.getParameter("operation");
	ComboPooledDataSource dataSource = (ComboPooledDataSource) request.getServletContext().getAttribute("dataSource");

	String responseMessage = null;
	PrintWriter writer = null;

	response.setStatus(200);
	response.setHeader("Content-Type", "application/json");
	response.setCharacterEncoding("UTF-8");

	try {
	    writer = response.getWriter();
	    conn = dataSource.getConnection();

	    responseMessage = executeDao(model);

	} catch (SQLException | IOException e) {
	    responseMessage = Util.createResponseMessage(e.getMessage());
	} finally {
	    writer.print(responseMessage);
	}

	return new FlowEmpty("");
    }

    abstract String executeDao(String json) throws SQLException;

}
