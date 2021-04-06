package com.ressubt.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class ApplicationHttpSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
	HttpSession session = se.getSession();
	session.setMaxInactiveInterval(60 * 60 * 24);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }

}
