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
import com.ressubt.model.Municipio;

public class JsonMunicipio implements Action {
    private static final Logger LOG = LogManager.getLogger(JsonMunicipio.class);

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	ServletContext servletContext = request.getServletContext();
	String codigouf = request.getParameter("codigouf");

	try {
	    Object municipioJson = servletContext.getAttribute(codigouf);

	    if (municipioJson == null) {
		ComboPooledDataSource dataSource = (ComboPooledDataSource) servletContext.getAttribute("dataSource");
		conn = dataSource.getConnection();
		st = conn.createStatement();
		rs = st.executeQuery("select codigo, descricao from municipio where uf = " + codigouf + " order by descricao");
		List<Municipio> listMunicipio = new ArrayList<Municipio>();

		while (rs.next()) {
		    int codigo = rs.getInt("codigo");
		    String descricao = rs.getString("descricao");
		    listMunicipio.add(new Municipio(codigo, descricao));
		}
		municipioJson = new Gson().toJson(listMunicipio);
		servletContext.setAttribute(codigouf, municipioJson);
	    }

	    response.setHeader("Content-Type", "application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().print(municipioJson);

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