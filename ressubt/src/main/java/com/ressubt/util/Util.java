package com.ressubt.util;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;

public class Util {

    private static final String VIEW_NO_ACTION = "ViewNoAction";
    public static final String ACTION = "action";
    public static final String VIEW = "view";

    private static ResourceBundle BUNDLE = ResourceBundle.getBundle("action.dependencies", Locale.getDefault());

    public static <T> T createInstance(Class<T> classe, String key) throws ServletException {
	try {

	    String nomeDaClasse = null;

	    if (BUNDLE.containsKey(key)) {
		nomeDaClasse = BUNDLE.getString(key);
	    } else {
		nomeDaClasse = BUNDLE.getString(VIEW_NO_ACTION);
	    }
	    Class<?> clazz = Class.forName(nomeDaClasse);

	    return clazz.asSubclass(classe).newInstance();
	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
	    throw new ServletException(e);
	}
    }

}