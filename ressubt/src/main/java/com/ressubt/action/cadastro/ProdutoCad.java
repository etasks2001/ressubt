package com.ressubt.action.cadastro;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ressubt.model.Produto;
import com.ressubt.util.BigDecimalEmptyDeserializer;
import com.ressubt.util.IntegerEmptyDeserializer;

public class ProdutoCad extends Cadastro<Produto> {

    void populatePreparedStatement(String model, PreparedStatement ps) throws SQLException {
	System.out.println(model);

	Gson gson = new GsonBuilder().registerTypeAdapter(BigDecimal.class, new BigDecimalEmptyDeserializer()).registerTypeAdapter(Integer.class, new IntegerEmptyDeserializer()).create();
	Produto produto = gson.fromJson(model, Produto.class);
	checkFields(produto);

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

    @Override
    void checkFields(Produto model) throws SQLException {

	String cod_item = model.getCod_item();
	String descr_item = model.getDescr_item();
	String cod_barra = model.getCod_barra();
	String unid_inv = model.getUnid_inv();
	String cod_ncm = model.getCod_ncm();
	BigDecimal aliq_icms = model.getAliq_icms();
	String cest = model.getCest();

	String errorMessage = "";

	if (isNullOrEmpty(cod_item)) {
	    errorMessage = "Código inválido.";
	} else if (isNullOrEmpty(descr_item)) {
	    errorMessage = "Descrição inválida.";
	} else if (isNullOrEmpty(cod_barra)) {
	    errorMessage = "Código de barras inválido.";
	} else if (isNullOrEmpty(unid_inv)) {
	    errorMessage = "Unidade inválida.";
	} else if (isNullOrEmpty(cod_ncm)) {
	    errorMessage = "NCM inválida.";
	} else if (isNullOrEmpty(aliq_icms)) {
	    errorMessage = "Alíquota ICMS inválida.";
	} else if (isNullOrEmpty(cest)) {
	    errorMessage = "CEST inválida.";
	}

	if (campoComErro(errorMessage)) {
	    throw new SQLException(errorMessage);
	}
    }
}
