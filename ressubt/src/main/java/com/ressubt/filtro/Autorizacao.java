package com.ressubt.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ressubt.control.HttpFlow;
import com.ressubt.control.Redirect;
import com.ressubt.util.Util;

@WebFilter(urlPatterns = "/control")
public class Autorizacao implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
	System.out.println("Autorizacao  ->>>-  Filtro");

	HttpServletRequest request = (HttpServletRequest) servletRequest;
	HttpServletResponse response = (HttpServletResponse) servletResponse;

	String parametro = request.getParameter(Util.ACTION);
	HttpSession session = request.getSession();

	boolean acaoNaoProtegida = acaoNaoProtegida(parametro);

	boolean usuarioNaoEstaLogado = usuarioNaoEstaLogado(session);
	if (usuarioNaoEstaLogado) {
	    if (acaoNaoProtegida) {
		HttpFlow redirect = new Redirect("ViewLogin");
		redirect.send(request, response);
		return;
	    }
	} else {
	    if (parametro.equals("ViewLogin")) {
		HttpFlow redirect = new Redirect("ViewMain");
		redirect.send(request, response);
		return;
	    }
	}
	chain.doFilter(request, response);
    }

    private boolean acaoNaoProtegida(String parametro) {
	return !(parametro.equals("ViewLogin") || parametro.equals("Login"));
    }

    private boolean usuarioNaoEstaLogado(HttpSession session) {
	return session.getAttribute("usuarioLogado") == null;
    }
}