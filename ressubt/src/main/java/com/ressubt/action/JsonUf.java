package com.ressubt.action;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ressubt.control.FlowEmpty;
import com.ressubt.control.Forward;
import com.ressubt.control.HttpFlow;
import com.ressubt.util.Util;

public class JsonUf implements Action {

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	try {

	    String uf = request.getParameter(Util.ACTION);
	    String municipio = request.getParameter("municipio");

	    String fileName = municipio;

	    if (municipio == null) {
		fileName = uf;
	    }

	    response.setHeader("Content-Type", "application/json");
	    response.setCharacterEncoding("UTF-8");

	    InputStream ufjson = request.getServletContext().getResourceAsStream("/WEB-INF/uf/" + fileName + ".json");

	    if (ufjson == null) {
		return new Forward("noaction.jsp");
	    } else {
		byte[] buffer = new byte[1024];

		for (int length = 0; (length = ufjson.read(buffer)) > 0;) {
		    response.getOutputStream().write(buffer, 0, length);
		}
		ufjson.close();
	    }

	} catch (IOException e) {
	    throw new ServletException(e);
	}

	return new FlowEmpty("");
    }
}