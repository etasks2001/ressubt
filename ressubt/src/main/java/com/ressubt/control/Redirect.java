package com.ressubt.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Redirect extends HttpFlow {

    public Redirect(String location) {
	super(location);
    }

    @Override
    public void send(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	try {
	    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);

	    response.sendRedirect("control?action=" + location);
	} catch (IOException e) {
	    throw new ServletException(e);
	}
    }
}
