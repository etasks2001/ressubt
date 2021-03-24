package com.ressubt.action.cadastro;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

import com.google.gson.Gson;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ressubt.action.Action;
import com.ressubt.control.FlowEmpty;
import com.ressubt.control.HttpFlow;
import com.ressubt.model.Finalidade;
import com.ressubt.util.Util;

public class FinalidadeCad implements Action {

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	String model = request.getParameter("model");
	String operation = request.getParameter("operation");
	String sql = Util.RESOURCE_BUNDLE.getString(this.getClass().getSimpleName() + "_" + operation);
	ComboPooledDataSource dataSource = (ComboPooledDataSource) request.getServletContext().getAttribute("dataSource");

	Connection conn = null;
	PreparedStatement ps = null;
	String responseMessage = null;
	PrintWriter writer = null;

	response.setStatus(200);
	response.setHeader("Content-Type", "application/json");
	response.setCharacterEncoding("UTF-8");

	try {
	    writer = response.getWriter();

	    conn = dataSource.getConnection();
	    ps = conn.prepareStatement(sql);

	    populatePreparedStatement(model, ps);

	    int total = ps.executeUpdate();

	    if (total > 0) {
		responseMessage = Util.createResponseMessage(0, "gravado com sucessoççç.");
	    } else {
		responseMessage = Util.createResponseMessage("erro gravação.");
	    }
	} catch (SQLException | IOException e) {
	    responseMessage = Util.createResponseMessage("erro gravação. " + e.getMessage());
	} finally {
	    DbUtils.closeQuietly(ps);
	    DbUtils.closeQuietly(conn);
	    conn = null;
	    writer.print(responseMessage);
	}

	return new FlowEmpty("");
    }

    private void populatePreparedStatement(String model, PreparedStatement ps) throws SQLException {
	System.out.println(model);
	Finalidade finalidade = new Gson().fromJson(model, Finalidade.class);

	ps.setString(1, finalidade.getDescricao());
	ps.setString(2, finalidade.getCodigo());
    }
}