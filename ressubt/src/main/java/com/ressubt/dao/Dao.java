package com.ressubt.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ressubt.model.Model;
import com.ressubt.util.Util;

public abstract class Dao<T extends Model, K> {
    String responseMessage = null;
    Connection connection = null;

    public Dao(Connection connection) {
	this.connection = connection;
    }

    public abstract List<T> getAll();

    public abstract T getRegistro(K codigo);

    public abstract String insert(T model);

    public abstract String update(T model);

    public abstract void delete(T model);

    abstract void checkFields(T model) throws SQLException;

    boolean isNullOrEmpty(String field) {
	return field == null || field.length() == 0;
    }

    boolean isNullOrEmpty(BigDecimal field) {
	return field == null || field.doubleValue() == 0;
    }

    boolean campoComErro(String errorMessage) {
	return errorMessage.length() > 0;
    }

    String checkGravacao(int total) {
	if (total > 0) {
	    return Util.createResponseMessage(0, "gravado com sucesso.");
	} else {
	    return Util.createResponseMessage("erro gravação.");
	}

    }

    static boolean isValidField(String field, String regex) {
	return field.matches(regex);
    }

    public static String REGEX_CODIGO_MUNICIPIO = "[0-9]{7}";
    public static String REGEX_CODIGO_VERSAO = "[0-9]{2}|[0-9]{1}";
    public static String REGEX_CNPJ = "[0-9]{2}[\\\\.]?[0-9]{3}[\\\\.]?[0-9]{3}[\\\\/]?[0-9]{4}[-]?[0-9]{2}";
    public static String REGEX_CNPJ_CPF = "([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})\r\n" + "";

    public static void main(String[] args) {
	System.out.println(Dao.isValidField("0000000", REGEX_CODIGO_MUNICIPIO));
	System.out.println(Dao.isValidField("00", REGEX_CODIGO_VERSAO));
	System.out.println(Dao.isValidField("0", REGEX_CODIGO_VERSAO));
	System.out.println(Dao.isValidField("66.666.666/1111-11", REGEX_CNPJ));

    }

}
