package com.ressubt.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ressubt.control.FlowEmpty;
import com.ressubt.control.HttpFlow;
import com.ressubt.jdbc.PostgreDataSource;
import com.ressubt.model.Finalidade;

public class JsonFinalidade implements Action {

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {

	try {
	    response.setHeader("Content-Type", "text/plain");
	    response.setCharacterEncoding("UTF-8");

	    PostgreDataSource source = new PostgreDataSource();

	    Connection connection = source.getConnectionPool().getConnection();
	    PreparedStatement ps = connection.prepareStatement("select * from finalidade");

	    ResultSet rs = ps.executeQuery();
	    List<Finalidade> finalidades = new ArrayList<Finalidade>();
	    while (rs.next()) {
		String codigo = rs.getString("codigo");
		String descricao = rs.getString("descricao");
		finalidades.add(new Finalidade(codigo, descricao));
	    }
	    connection.close();

	    Gson gson = new Gson();

	    String json = gson.toJson(finalidades);
	    response.setContentType("application/json");
	    response.getWriter().print(json);

	} catch (SQLException | IOException e) {
	    throw new ServletException(e);
	}
	return new FlowEmpty("");
    }
}
