package com.arrkgroup.apps.config;



import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionCreationEvent;
import org.springframework.stereotype.Component;

@Component
public class LoginListener implements ApplicationListener<SessionCreationEvent> {

    @Override
    public void onApplicationEvent(SessionCreationEvent event)
    {
    	System.out.println("Seesion Created");
      
    }

}
