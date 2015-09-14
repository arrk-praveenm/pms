package com.arrkgroup.apps.config;

import javax.servlet.http.HttpSessionEvent;

import org.springframework.security.web.session.HttpSessionEventPublisher;

public class SessionListener extends HttpSessionEventPublisher{
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		//setting session time interval for 30 minutes
		 event.getSession().setMaxInactiveInterval(60*30);
	      super.sessionCreated(event);
	}

}
