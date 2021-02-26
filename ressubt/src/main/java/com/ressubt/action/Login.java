package com.ressubt.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	    System.out.println("Usuário existe ");

	    HttpSession session = request.getSession();
	    session.setAttribute("usuarioLogado", usuario);
	    System.out.println(session.getClass().getName());
	    System.out.println(session.getId());
	    System.out.println(usuario);

	    return new Redirect("ViewMain");
	} else {
	    return new Redirect("ViewLogin");
	}

    }

}
