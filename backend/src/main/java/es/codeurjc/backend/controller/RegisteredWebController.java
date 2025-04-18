package es.codeurjc.backend.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.codeurjc.backend.dto.user.NewUserDTO;
import es.codeurjc.backend.dto.user.UserDTO;
import es.codeurjc.backend.service.UserService;

@Controller
public class RegisteredWebController {

    @Autowired
    private UserService userService;

    @Autowired
	private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("error", "");
        return "register";
    }

    @PostMapping("/user/new")
    public String registerMethod(NewUserDTO newUserDTO, Model model) throws IOException, SQLException {

        UserDTO userDTO = createOrReplaceUser(newUserDTO,null,null);

        return "redirect:/user/" + userDTO.id();
    }

    @GetMapping("/user/{id}/photo")
    public ResponseEntity<Object> getUserPhoto(@PathVariable long id) throws SQLException {

        Resource userImage = userService.getUserImage(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(userImage);

    }

    @GetMapping("/edituser/{id}")
    public String editUserPage(Model model, @PathVariable long id) {

        try {
            UserDTO userDTO = userService.getUser(id);
            model.addAttribute("user", userDTO);
            return "editUser";
        } catch (NoSuchElementException e) {
            return "redirect:/";
        }

    }

    @PostMapping("/edituser/{id}")
    public String editUser(@RequestParam(value = "removeImage", required = false) boolean removeImage, Model model, @PathVariable long id, RedirectAttributes redirectAttributes,
            NewUserDTO newUserDTO) throws IOException, SQLException {

        if (removeImage) {
            userService.deleteUserImage(id);
        }

       createOrReplaceUser(newUserDTO,id, removeImage);

        redirectAttributes.addFlashAttribute("successMessage", "Your profile has been edited successfully.");
        return "redirect:/";

    }

    private UserDTO createOrReplaceUser(NewUserDTO newUserDTO, Long userId, Boolean removeImage) throws SQLException, IOException {
        return userService.UserCreationReplacement(userId, newUserDTO, removeImage, passwordEncoder);
    }
}