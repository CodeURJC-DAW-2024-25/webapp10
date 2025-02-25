package es.codeurjc.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;

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
