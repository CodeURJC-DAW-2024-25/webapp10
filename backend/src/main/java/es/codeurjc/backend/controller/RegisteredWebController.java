package es.codeurjc.backend.controller;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.backend.model.User;


@Controller
public class RegisteredWebController {


    ArrayList<User> users=new ArrayList<>();
    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        return "register";
    }
 
    @PostMapping("/user/new")
    public String registerMethod(Model model, @ModelAttribute User user) {


        if ((user.getAge()<0) || (user.getAge()>100)){
            return "redirect:/register"; 
        }
        users.add(user);
        model.addAttribute("user", user);
        return "userPage";
    }
    


}
