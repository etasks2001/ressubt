package com.ressubt.action;

import java.io.IOException;
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
import com.ressubt.model.Finalidade;

public class JsonFinalidade implements Action {

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection con = null;
	try {
	    response.setHeader("Content-Type", "text/plain");
	    response.setCharacterEncoding("UTF-8");

	    con = new PostgreDataSource().getConnectionPool().getConnection();
	    ps = con.prepareStatement("select codigo,descricao from finalidade");
	    rs = ps.executeQuery();

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
	    DbUtils.closeQuietly(con);
	}
	return new FlowEmpty("");
    }
}
