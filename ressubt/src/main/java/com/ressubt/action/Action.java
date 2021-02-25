package com.ressubt.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ressubt.control.HttpFlow;

public interface Action {

    HttpFlow exec(HttpServletRequest request, HttpServletResponse response) throws ServletException;
}
