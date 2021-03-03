package com.ressubt.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

import com.google.gson.Gson;
import com.ressubt.control.FlowEmpty;
import com.ressubt.control.HttpFlow;
import com.ressubt.jdbc.PostgreDataSource;
import com.ressubt.model.Municipio;

public class JsonMunicipio implements Action {

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String codigouf = request.getParameter("codigouf");
	try {

	    conn = PostgreDataSource.getConnectionPool().getConnection();
	    ps = conn.prepareStatement("select codigo, descricao from municipio where uf = ?");
	    ps.setInt(1, Integer.parseInt(codigouf));

	    rs = ps.executeQuery();

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
	}

	return new FlowEmpty("");
    }

}
