package es.codeurjc.backend.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.backend.dto.NewUserDTO;
import es.codeurjc.backend.dto.UserDTO;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.UserService;


@Controller
public class RegisteredWebController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("error","");
        return "register";
    }
 
    @PostMapping("/user/new")
    public String registerMethod(NewUserDTO newUserDTO,Model model) throws IOException, SQLException {

        UserDTO userDTO= userService.createUser(newUserDTO);

        return "redirect:/user/"+ userDTO.id();
    }

    @GetMapping("/user/{id}/photo")
	public ResponseEntity<Object> getUserPhoto(@PathVariable long id) throws SQLException {
	
        Resource userImage=userService.getUserImage(id);
	
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
					.body(userImage);
		
	}
}