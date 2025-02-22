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

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
	public String showlogin() {
		return "login";
	}

    @PostMapping("/login")
    public String login (Model model,@RequestParam String email, @RequestParam String password){
        User user= userService.userCorrect(email,password);
        if (user!=null){
            model.addAttribute("user",user);
            return "userPage";
        }
        return "loginerror";
    }

	@RequestMapping("/loginerror")
	public String showloginerror() {
		return "loginerror";
	}
    
}
