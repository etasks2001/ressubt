package com.ressubt.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

import com.google.gson.Gson;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ressubt.control.FlowEmpty;
import com.ressubt.control.HttpFlow;
import com.ressubt.model.Municipio;

public class JsonMunicipio implements Action {

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	Connection conn = null;
	Statement ps = null;
	ResultSet rs = null;
	String codigouf = request.getParameter("codigouf");
	ComboPooledDataSource ds = (ComboPooledDataSource) request.getServletContext().getAttribute("dataSource");
	try {

	    conn = ds.getConnection();// DBUtil.getDataSource().getConnection();
	    ps = conn.createStatement();

	    rs = ps.executeQuery("select codigo, descricao from municipio where uf = " + codigouf);

	    List<Municipio> listMunicipio = new ArrayList<Municipio>();
	    while (rs.next()) {
		int codigo = rs.getInt("codigo");
		String descricao = rs.getString("descricao");

		listMunicipio.add(new Municipio(codigo, descricao));

	    }

	    Gson gson = new Gson();
	    String municipiosJson = gson.toJson(listMunicipio);

	    response.setHeader("Content-Type", "application/json");
	    response.setCharacterEncoding("UTF-8");
	    PrintWriter writer = response.getWriter();
	    writer.print(municipiosJson);

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