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
import com.ressubt.model.Pais;

public class JsonPais implements Action {
    private static final Logger LOG = LogManager.getLogger(JsonPais.class);

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	ServletContext servletContext = request.getServletContext();

	try {
	    Object paisJson = servletContext.getAttribute("pais");

	    if (paisJson == null) {
		ComboPooledDataSource dataSource = (ComboPooledDataSource) servletContext.getAttribute("dataSource");
		conn = dataSource.getConnection();
		st = conn.createStatement();
		rs = st.executeQuery("select codigo, descricao from pais order by descricao");
		List<Pais> listPais = new ArrayList<Pais>();

		while (rs.next()) {
		    String codigo = rs.getString("codigo");
		    String descricao = rs.getString("descricao");
		    listPais.add(new Pais(codigo, descricao));
		}
		paisJson = new Gson().toJson(listPais);
		servletContext.setAttribute("pais", paisJson);
	    }

	    response.setHeader("Content-Type", "application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().print(paisJson);

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