package com.ressubt.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

import com.google.gson.Gson;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ressubt.control.FlowEmpty;
import com.ressubt.control.HttpFlow;
import com.ressubt.model.Pais;

public class JsonPais implements Action {

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	ServletContext servletContext = request.getServletContext();
	ComboPooledDataSource dataSource = (ComboPooledDataSource) servletContext.getAttribute("dataSource");
	Connection conn = null;
	ResultSet rs = null;
	Statement ps = null;

	try {
	    conn = dataSource.getConnection();
	    ps = conn.createStatement();
	    rs = ps.executeQuery("select codigo, descricao from pais order by descricao asc");
	    List<Pais> listPais = new ArrayList<Pais>();

	    while (rs.next()) {
		String codigo = rs.getString("codigo");
		String descricao = rs.getString("descricao");

		listPais.add(new Pais(codigo, descricao));

	    }

	    Gson gson = new Gson();
	    String pais = gson.toJson(listPais);

	    response.setHeader("Content-Type", "application/json");
	    PrintWriter writer = response.getWriter();
	    writer.print(pais);

	} catch (SQLException | IOException e) {
	    throw new ServletException(e);
	} finally {
	    DbUtils.closeQuietly(rs);
	    DbUtils.closeQuietly(ps);
	    DbUtils.closeQuietly(conn);
	    conn = null;
	}
	return new FlowEmpty("");
    }
}