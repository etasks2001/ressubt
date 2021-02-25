package com.ressubt.control;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class HttpFlow {
    protected String location;

    public HttpFlow(String location) {
	this.location = location;
    }

    public abstract void send(HttpServletRequest request, HttpServletResponse response) throws ServletException;
}
