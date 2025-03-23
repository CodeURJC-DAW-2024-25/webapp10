package es.codeurjc.backend.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.codeurjc.backend.dto.ticket.TicketDTO;
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

private UserDTO createOrReplaceUser(NewUserDTO newUserDTO, Long userId, Boolean removeImage)
			throws SQLException, IOException {

		boolean image = false;
        String userName= newUserDTO.userName();
        String password= null;
        Integer numTicketsBought = 0;
        String favoriteGenre= "None";
        List<TicketDTO>tickets=null;
        List<String> roles= List.of("USER");

        if (newUserDTO.password() != null && !newUserDTO.password().isEmpty()) {
            password = passwordEncoder.encode(newUserDTO.password());
        }
        
		if (userId != null) {
			UserDTO olduser = userService.getUser(userId);
			image = removeImage ? false : olduser.image();
            userName= olduser.userName();
            password= olduser.password();
            numTicketsBought= olduser.numTicketsBought();
            favoriteGenre= olduser.favoriteGenre();
            tickets= olduser.tickets();
            roles= olduser.roles();
		}

       

		UserDTO userDTO = new UserDTO(userId,
				newUserDTO.fullName(), userName, newUserDTO.phone(),
				newUserDTO.email(), password, newUserDTO.age(),
				numTicketsBought, favoriteGenre, image, tickets, roles);

		UserDTO newUser = userService.createOrReplaceUser(userId, userDTO);

		MultipartFile imageField = newUserDTO.profilePhoto();
		if (!imageField.isEmpty()) {
			userService.createUserImage(newUser.id(), imageField.getInputStream(), imageField.getSize());
		} 

		return newUser;
	}
}