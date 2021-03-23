package com.ressubt.util;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;

import com.google.gson.Gson;
import com.ressubt.model.ResponseMessage;

public class Util {

    private static final String VIEW_NO_ACTION = "ViewNoAction";
    public static final String ACTION = "action";
    public static final String VIEW = "view";

    public static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("action.dependencies", Locale.getDefault());

    public static <T> T createInstance(Class<T> classe, String key) throws ServletException {
	try {

	    String nomeDaClasse = null;

	    if (RESOURCE_BUNDLE.containsKey(key)) {
		nomeDaClasse = RESOURCE_BUNDLE.getString(key);
	    } else {
		nomeDaClasse = RESOURCE_BUNDLE.getString(VIEW_NO_ACTION);
	    }
	    Class<?> clazz = Class.forName(nomeDaClasse);

	    return clazz.asSubclass(classe).newInstance();
	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
	    throw new ServletException(e);
	}
    }

    public static String createResponseMessage(String message) {
	return createResponseMessage(-1, message);

    }

    public static String createResponseMessage(int codigo, String message) {
	return new Gson().toJson(new ResponseMessage(codigo, message));

    }

}