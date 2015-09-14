package com.arrkgroup.apps.ajaxcalls;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class SessionManageController {
    @Autowired
	HttpSession session;
    @Autowired
    HttpServletRequest request;
    
	@RequestMapping(value = "/ajax/session")
	public String login() {
		
		
		
		session.setMaxInactiveInterval(60*30);
		System.out.println(request.getParameter("username"));
		System.out.println(request.getParameter("password"));

        return "fragments/default";
    }
}
