package com.ressubt.action.cadastro;

import java.sql.Connection;
import java.sql.SQLException;

import com.ressubt.dao.ProdutoDao;
import com.ressubt.model.Produto;

public class ProdutoCad extends Cadastro<Produto> {

    void executeDao(String json, Connection connection, String operation) throws SQLException {
	System.out.println(json);

	Produto model = fromJson(json, Produto.class);
	ProdutoDao dao = new ProdutoDao();

	if (operation.equals("i")) {
	    dao.insert(model, connection);
	} else if (operation.equals("u")) {
	    dao.update(model, connection);
	}

    }
}