package com.ressubt.control;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ressubt.action.Action;
import com.ressubt.util.Util;

@WebServlet(urlPatterns = "/control")
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	String parametro = request.getParameter(Util.ACTION);

	Action acao = Util.createInstance(Action.class, parametro);

	HttpFlow flow = acao.exec(request, response);

	flow.send(request, response);
    }

}