package com.ressubt.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ressubt.control.Forward;
import com.ressubt.control.HttpFlow;
import com.ressubt.control.Redirect;
import com.ressubt.model.Usuario;

public class Login implements Action {

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	String email = request.getParameter("email");
	String senha = request.getParameter("senha");

	Usuario usuario = new Usuario(email, senha);

	System.out.println(email + senha);
	if (email.equals("mauro") && senha.equals("123")) {
	    HttpSession session = request.getSession();
	    session.setAttribute("usuarioLogado", usuario);

	    return new Redirect("ViewMain");
	} else {
	    request.setAttribute("email", email);
	    request.setAttribute("senha", senha);
	    request.setAttribute("message", "E-mail ou senha incorretos.");

	    return new Forward("login.jsp");
	}
    }
}