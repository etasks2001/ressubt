package com.ressubt.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ressubt.control.HttpFlow;
import com.ressubt.control.Redirect;

public class Logout implements Action {

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	HttpSession session = request.getSession();
	session.invalidate();
	return new Redirect("ViewLogin");
    }

}
