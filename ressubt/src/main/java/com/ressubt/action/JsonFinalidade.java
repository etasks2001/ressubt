package com.ressubt.action;

import java.io.IOException;
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
import com.ressubt.model.Finalidade;

public class JsonFinalidade implements Action {

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	Statement ps = null;
	ResultSet rs = null;
	Connection conn = null;
	ComboPooledDataSource ds = (ComboPooledDataSource) request.getServletContext().getAttribute("dataSource");
	try {

	    response.setHeader("Content-Type", "text/plain");
//	    response.setHeader("Cache-Control", "public, max-age=31557600");
	    response.setCharacterEncoding("UTF-8");

	    conn = ds.getConnection();// DBUtil.getDataSource().getConnection();
	    ps = conn.createStatement();
	    rs = ps.executeQuery("select codigo,descricao from finalidade");

	    List<Finalidade> listFinalidade = new ArrayList<Finalidade>();
	    while (rs.next()) {
		String codigo = rs.getString("codigo");
		String descricao = rs.getString("descricao");
		listFinalidade.add(new Finalidade(codigo, descricao));
	    }

	    Gson gson = new Gson();

	    String json = gson.toJson(listFinalidade);
	    response.setContentType("application/json");
	    response.getWriter().print(json);

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
