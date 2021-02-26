package com.ressubt.action.view;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ressubt.action.Action;
import com.ressubt.control.Forward;
import com.ressubt.control.HttpFlow;

public class LoginForm implements Action {

    @Override
    public HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException {

	return new Forward("login.jsp");
    }
}
