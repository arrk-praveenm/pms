package com.arrkgroup.apps.home;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.arrkgroup.apps.layoutSupport.Layout;


@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Principal principal) {

		return principal != null ? "fragments/default" : "login/login";
	}
}
