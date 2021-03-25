package com.ressubt.action.cadastro;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.ressubt.model.Produto;

public class ProdutoCad extends Cadastro {

//    @Override
//    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
//	String model = request.getParameter("model");
//	String operation = request.getParameter("operation");
//	String sql = Util.RESOURCE_BUNDLE.getString(this.getClass().getSimpleName() + "_" + operation);
//	ComboPooledDataSource dataSource = (ComboPooledDataSource) request.getServletContext().getAttribute("dataSource");
//
//	Connection conn = null;
//	PreparedStatement ps = null;
//	String responseMessage = null;
//	PrintWriter writer = null;
//
//	response.setStatus(200);
//	response.setHeader("Content-Type", "application/json");
//	response.setCharacterEncoding("UTF-8");
//
//	try {
//	    writer = response.getWriter();
//
//	    conn = dataSource.getConnection();
//	    ps = conn.prepareStatement(sql);
//
//	    populatePreparedStatement(model, ps);
//
//	    int total = ps.executeUpdate();
//
//	    if (total > 0) {
//		responseMessage = Util.createResponseMessage(0, "gravado com sucessoççç.");
//	    } else {
//		responseMessage = Util.createResponseMessage("erro gravação.");
//	    }
//	} catch (SQLException | IOException e) {
//	    responseMessage = Util.createResponseMessage("erro gravação. " + e.getMessage());
//	} finally {
//	    DbUtils.closeQuietly(ps);
//	    DbUtils.closeQuietly(conn);
//	    conn = null;
//	    writer.print(responseMessage);
//	}
//
//	return new FlowEmpty("");
//    }

    void populatePreparedStatement(String model, PreparedStatement ps) throws SQLException {
	System.out.println(model);
	Produto produto = new Gson().fromJson(model, Produto.class);

	ps.setInt(1, produto.getContribuinte());
	ps.setString(2, produto.getCod_item());
	ps.setString(3, produto.getDescr_item());
	ps.setString(4, produto.getCod_barra());
	ps.setString(5, produto.getUnid_inv());
	ps.setString(6, produto.getCod_ncm());
	ps.setBigDecimal(7, produto.getAliq_icms());
	ps.setString(8, produto.getCest());
	if (produto.getSk() > 0) {
	    ps.setInt(9, produto.getSk());
	}

    }
}
