package es.codeurjc.backend.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

import es.codeurjc.backend.model.Concert;
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
    public String registerMethod(
        @RequestParam String fullName,
        @RequestParam String userName,
        @RequestParam Integer phone,
        @RequestParam String email,
        @RequestParam String password,
        @RequestParam Integer age,
        @RequestParam MultipartFile profilePhoto,
        Model model) throws IOException {
        
        if (userService.userExists(userName)){
            model.addAttribute("error", "Name already exists");
            return "register";
        }

        if (fullName == null || fullName.isEmpty()) {
            model.addAttribute("error", "Fill the gap name");
            return "register";
        } 
          
        if (userName == null || userName.isEmpty()) {
            model.addAttribute("error", "Fill the gap username");
            return "register";

        }

        if (phone==null || String.valueOf(phone).length()!=9) {
            model.addAttribute("error", "Phone number must be 9 digits");
            return "register";
        }

        if (email == null || email.isEmpty() || !email.contains("@") || !email.contains(".com")) {
            model.addAttribute("error", "Write a valid email");
            return "register";
        }

        if (password == null || password.isEmpty() ) {
            model.addAttribute("error", "Fill the gap password");
            return "register";
        }

        if (age==null || age< 0 || age > 110) {
            model.addAttribute("error", "Enter an age between 0-100");
            return "register";
        }

        User newUser = new User(fullName, userName, phone, email, passwordEncoder.encode(password), age, "USER");

        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            newUser.setProfilePhoto(BlobProxy.generateProxy(profilePhoto.getInputStream(), profilePhoto.getSize()));
            newUser.setImage(true);
        }
        
        userRepository.save(newUser);
        return "redirect:/";
    }

    @GetMapping("/user/{id}/photo")
	public ResponseEntity<Object> getUserPhoto(@PathVariable long id) throws SQLException {
		Optional<User> user = userRepository.findById(id);

		if (user.isPresent() && user.get().getProfilePhoto() != null) {
			Resource file = new InputStreamResource(user.get().getProfilePhoto().getBinaryStream());

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
					.contentLength(user.get().getProfilePhoto().length())
					.body(file);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}