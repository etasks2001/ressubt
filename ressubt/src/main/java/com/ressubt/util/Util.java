package com.ressubt.util;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;

public class Util {
    private static final String SEM_ACAO = "SemAcao";
    public static final String ACAO = "acao";
    public static final String VIEW = "view";

    private static ResourceBundle BUNDLE = ResourceBundle.getBundle("dependencies", Locale.getDefault());

    public static <T> T createInstance(Class<T> classe, String key) throws ServletException {
	try {
	    String nomeDaClasse = BUNDLE.getString(SEM_ACAO);
	    if (key != null) {
		nomeDaClasse = BUNDLE.getString(key);
	    }
	    Class<?> clazz = Class.forName(nomeDaClasse);
	    return clazz.asSubclass(classe).newInstance();
	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
	    throw new ServletException(e);
	}
    }
}