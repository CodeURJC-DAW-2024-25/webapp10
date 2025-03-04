package es.codeurjc.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
public class LoginWebController {


    @RequestMapping("/login")
	public String showlogin() {
		return "login";
	}

	@RequestMapping("/loginerror")
	public String showloginerror() {
		return "loginerror";
	}
    
}
