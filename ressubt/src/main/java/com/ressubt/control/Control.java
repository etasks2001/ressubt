package com.ressubt.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/action")
public class Control extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	String view = request.getParameter("view");

	if (parametroViewEncontrado(view)) {
	    if (view.equals("index")) {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
		dispatcher.forward(request, response);
	    }
	}
    }

    private boolean parametroViewEncontrado(String view) {
	return view != null;
    }
}