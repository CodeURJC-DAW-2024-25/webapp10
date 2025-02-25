package es.codeurjc.backend.controller;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.Collections;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.UserRepository;
import es.codeurjc.backend.service.UserService;


@Controller
public class RegisteredWebController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("error","");
        return "register";
    }
 
    @PostMapping("/user/new")
    public String registerMethod(Model model, @ModelAttribute User user, MultipartFile profilePhoto) throws IOException {

        
        if (userService.userExists(user.getUserName())){
            model.addAttribute("error", "Name already exists");
            return "register";
        }

        if (user.getFullName() == null || user.getFullName().isEmpty()) {
            model.addAttribute("error", "Fill the gap name");
            return "register";
        } 
          
        if (user.getUserName() == null || user.getUserName().isEmpty()) {
            model.addAttribute("error", "Fill the gap username");
            return "register";

        }
    
        Integer phone=user.getPhone();
        if (phone==null || String.valueOf(phone).length()!=9) {
            model.addAttribute("error", "Phone number must be 9 digits");
            return "register";
        }

        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@") || !user.getEmail().contains(".com")) {
            model.addAttribute("error", "Write a valid email");
            return "register";
        }

        if (user.getEncodedPassword() == null || user.getEncodedPassword().isEmpty() ) {
            model.addAttribute("error", "Fill the gap password");
            return "register";
        }

        /* if (!profilePhoto.isEmpty()) {
			user.setProfilePhoto(BlobProxy.generateProxy(profilePhoto.getInputStream(), profilePhoto.getSize()));
			user.setImage(true);
		} */
        Integer age = user.getAge(); 
        if (age==null || age< 0 || age > 110) {
            model.addAttribute("error", "Enter an age between 0-100");
            return "register";
        } 
        
        userRepository.save(new User(user.getFullName(),user.getUserName(),user.getPhone(),user.getEmail(), passwordEncoder.encode(user.getEncodedPassword()),user.getAge(),"USER"));
        return "redirect:/user/"+user.getId();

    }

    
    


}