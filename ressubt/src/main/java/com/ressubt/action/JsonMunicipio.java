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
import com.ressubt.model.Municipio;

public class JsonMunicipio implements Action {
    private static Logger log = LogManager.getLogger(JsonMunicipio.class);

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	Connection conn = null;
	Statement ps = null;
	ResultSet rs = null;
	String codigouf = request.getParameter("codigouf");

	try {
	    ServletContext servletContext = request.getServletContext();
	    Object municipioJson = servletContext.getAttribute(codigouf);
	    ComboPooledDataSource ds = (ComboPooledDataSource) servletContext.getAttribute("dataSource");

	    if (municipioJson == null) {
		System.out.println("selecting from database " + codigouf);
		conn = ds.getConnection();
		ps = conn.createStatement();

		rs = ps.executeQuery("select codigo, descricao from municipio where uf = " + codigouf);

		municipioJson = createJson(rs);
		servletContext.setAttribute(codigouf, municipioJson);
	    }

	    response.setHeader("Content-Type", "application/json");
	    response.setCharacterEncoding("UTF-8");
	    PrintWriter writer = response.getWriter();
	    writer.print(municipioJson);

	    return new FlowEmpty("");
	} catch (SQLException | IOException e) {
	    log.error("SEVERE", e);
	    throw new ServletException(e);

	} finally {
	    DbUtils.closeQuietly(rs);
	    DbUtils.closeQuietly(ps);
	    DbUtils.closeQuietly(conn);
	    conn = null;
	}

    }

    private Object createJson(ResultSet rs) throws SQLException {
	List<Municipio> listMunicipio = new ArrayList<Municipio>();
	while (rs.next()) {
	    int codigo = rs.getInt("codigo");
	    String descricao = rs.getString("descricao");

	    listMunicipio.add(new Municipio(codigo, descricao));
	}
	return new Gson().toJson(listMunicipio);
    }
}