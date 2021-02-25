package com.ressubt.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FlowJson extends HttpFlow {

    public FlowJson(String location) {
	super(location);
    }

    @Override
    public void send(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	try {
	    response.setContentType("application/json");
	    response.getWriter().print(location);

	} catch (IOException e) {
	    throw new ServletException(e);
	}
    }

}
