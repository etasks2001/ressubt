package com.ressubt.action.query;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ressubt.action.Action;
import com.ressubt.control.FlowEmpty;
import com.ressubt.control.HttpFlow;
import com.ressubt.model.Model;
import com.ressubt.model.Usuario;
import com.ressubt.util.Util;

public abstract class Query<T extends Model> implements Action {

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {

	String parameter = request.getParameter("p");
	String page = request.getParameter("page");
	String order = request.getParameter("o");

	Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");

	ComboPooledDataSource dataSource = (ComboPooledDataSource) request.getServletContext().getAttribute("dataSource");

	Object responseMessage = null;
	PrintWriter writer = null;

	Map<String, String> parameters = new HashMap<String, String>();

	parameters.put("parameter", parameter);
	parameters.put("page", page);
	parameters.put("currentContribuinte", usuario.getCurrentContribuinte());
	parameters.put("order", order);

	response.setStatus(200);
	response.setHeader("Content-Type", "application/json");
	response.setCharacterEncoding("UTF-8");

	try {
	    writer = response.getWriter();
	    Connection connection = dataSource.getConnection();

	    responseMessage = executeDaoQuery(connection, parameters);

	} catch (SQLException | IOException e) {
	    responseMessage = Util.createResponseMessage(e.getMessage());
	    e.printStackTrace();
	} finally {
	    writer.print(responseMessage);
	}

	return new FlowEmpty("");
    }

    abstract Object executeDaoQuery(Connection connection, Map<String, String> parameters) throws SQLException;

}