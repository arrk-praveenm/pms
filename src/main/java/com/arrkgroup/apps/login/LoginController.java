package com.arrkgroup.apps.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.arrkgroup.apps.layoutSupport.Layout;
@Layout(value = "login/login")
@Controller
public class LoginController {

	@RequestMapping(value = {"login",})
	public String login() {
        return "login/login";
    }
}
