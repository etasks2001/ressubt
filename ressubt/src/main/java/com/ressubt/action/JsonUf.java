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
import com.ressubt.model.Uf;

public class JsonUf implements Action {

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	Statement ps = null;
	ResultSet rs = null;
	Connection conn = null;
	ComboPooledDataSource ds = (ComboPooledDataSource) request.getServletContext().getAttribute("dataSource");
	try {
	    conn = ds.getConnection();// DBUtil.getDataSource().getConnection();

	    response.setHeader("Content-Type", "application/json");
	    response.setCharacterEncoding("UTF-8");

	    ps = conn.createStatement();
	    rs = ps.executeQuery("select codigo, descricao from uf order by descricao");
	    List<Uf> listUf = new ArrayList<Uf>();
	    while (rs.next()) {
		int codigo = rs.getInt("codigo");
		String descricao = rs.getString("descricao");
		listUf.add(new Uf(codigo, descricao));
	    }

	    Gson gson = new Gson();
	    String json = gson.toJson(listUf);
	    PrintWriter writer = response.getWriter();
	    writer.print(json);
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