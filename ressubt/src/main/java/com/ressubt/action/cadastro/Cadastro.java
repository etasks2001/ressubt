package com.ressubt.action.cadastro;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ressubt.action.Action;
import com.ressubt.control.FlowEmpty;
import com.ressubt.control.HttpFlow;
import com.ressubt.model.Model;
import com.ressubt.util.BigDecimalEmptyDeserializer;
import com.ressubt.util.IntegerEmptyDeserializer;
import com.ressubt.util.Util;

public abstract class Cadastro<T extends Model> implements Action {

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {

	String model = request.getParameter("model");
	String operation = request.getParameter("operation");
	ComboPooledDataSource dataSource = (ComboPooledDataSource) request.getServletContext().getAttribute("dataSource");

	String responseMessage = null;
	PrintWriter writer = null;

	response.setStatus(200);
	response.setHeader("Content-Type", "application/json");
	response.setCharacterEncoding("UTF-8");

	try {
	    writer = response.getWriter();
	    Connection connection = dataSource.getConnection();

	    executeDao(model, connection, operation);
	    responseMessage = Util.createResponseMessage(0, "gravado com sucesso.");
	} catch (SQLException | IOException e) {
	    responseMessage = Util.createResponseMessage(e.getMessage());
	} finally {
	    writer.print(responseMessage);
	}

	return new FlowEmpty("");
    }

    abstract void executeDao(String json, Connection conn, String operation) throws SQLException;

    T fromJson(String json, Class<T> clazzOf) {
	Gson gson = new GsonBuilder().registerTypeAdapter(BigDecimal.class, new BigDecimalEmptyDeserializer()).registerTypeAdapter(Integer.class, new IntegerEmptyDeserializer()).create();

	T model = gson.fromJson(json, clazzOf);

	return model;
    }
}