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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ressubt.control.FlowEmpty;
import com.ressubt.control.HttpFlow;
import com.ressubt.model.Uf;

public class JsonUf implements Action {
    private static final Logger log = LogManager.getLogger(JsonFinalidade.class);

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	Statement ps = null;
	ResultSet rs = null;
	Connection conn = null;

	try {
	    ServletContext servletContext = request.getServletContext();
	    Object ufJson = servletContext.getAttribute("uf");
	    if (ufJson == null) {
		ComboPooledDataSource ds = (ComboPooledDataSource) servletContext.getAttribute("dataSource");
		conn = ds.getConnection();

		ps = conn.createStatement();
		rs = ps.executeQuery("select codigo, descricao from uf order by descricao");

		ufJson = createJson(rs);
		servletContext.setAttribute("uf", ufJson);
	    }

	    response.setHeader("Content-Type", "application/json");
	    response.setCharacterEncoding("UTF-8");

	    PrintWriter writer = response.getWriter();
	    writer.print(ufJson);

	    return new FlowEmpty("");
	} catch (SQLException | IOException e) {
	    log.error("SEVERO", e);
	    throw new ServletException(e);
	} finally {
	    DbUtils.closeQuietly(rs);
	    DbUtils.closeQuietly(ps);
	    DbUtils.closeQuietly(conn);
	    conn = null;
	}
    }

    private Object createJson(ResultSet rs) throws SQLException {
	List<Uf> listUf = new ArrayList<Uf>();

	while (rs.next()) {
	    int codigo = rs.getInt("codigo");
	    String descricao = rs.getString("descricao");

	    listUf.add(new Uf(codigo, descricao));
	}

	return new Gson().toJson(listUf);
    }
}