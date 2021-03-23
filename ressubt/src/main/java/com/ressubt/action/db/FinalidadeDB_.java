package com.ressubt.action.db;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ressubt.action.Action;
import com.ressubt.control.FlowEmpty;
import com.ressubt.control.HttpFlow;
import com.ressubt.model.Finalidade;
import com.ressubt.util.Util;

public class FinalidadeDB_ implements Action {

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	String codigo = request.getParameter("codigo");
	String descricao = request.getParameter("descricao");
	Finalidade finalidade = new Finalidade(codigo, descricao);

	String operation = request.getParameter("operation");
	String sql = Util.RESOURCE_BUNDLE.getString(FinalidadeDB_.class.getSimpleName() + operation);
	ComboPooledDataSource dataSource = (ComboPooledDataSource) request.getServletContext().getAttribute("dataSource");

	Connection conn = null;
	PreparedStatement ps = null;
	String responseMessage = null;
	PrintWriter writer = null;
	try {
	    response.setStatus(200);
	    response.setHeader("Content-Type", "application/jon");
	    response.setCharacterEncoding("UTF-8");
	    writer = response.getWriter();

	    conn = dataSource.getConnection();
	    ps = conn.prepareStatement(sql);

	    ps.setString(1, finalidade.getDescricao());
	    ps.setString(2, finalidade.getCodigo());

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
}