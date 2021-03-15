package com.ressubt.action;

import java.io.IOException;
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
    private static final Logger LOG = LogManager.getLogger(JsonFinalidade.class);

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	ServletContext servletContext = request.getServletContext();

	try {
	    Object ufJson = servletContext.getAttribute("uf");

	    if (ufJson == null) {
		ComboPooledDataSource dataSource = (ComboPooledDataSource) servletContext.getAttribute("dataSource");
		conn = dataSource.getConnection();
		st = conn.createStatement();
		rs = st.executeQuery("select codigo, descricao from uf order by descricao");
		List<Uf> listUf = new ArrayList<Uf>();

		while (rs.next()) {
		    int codigo = rs.getInt("codigo");
		    String descricao = rs.getString("descricao");
		    listUf.add(new Uf(codigo, descricao));
		}
		ufJson = new Gson().toJson(listUf);
		servletContext.setAttribute("uf", ufJson);
	    }

	    response.setHeader("Content-Type", "application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().print(ufJson);

	    return new FlowEmpty("");
	} catch (SQLException | IOException e) {
	    LOG.error("SEVERE", e);
	    throw new ServletException(e);
	} finally {
	    DbUtils.closeQuietly(rs);
	    DbUtils.closeQuietly(st);
	    DbUtils.closeQuietly(conn);
	    conn = null;
	}
    }
}