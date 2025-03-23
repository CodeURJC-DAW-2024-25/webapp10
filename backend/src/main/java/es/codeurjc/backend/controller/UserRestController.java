package es.codeurjc.backend.controller;

import java.net.URI;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.backend.dto.user.NewUserDTO;
import es.codeurjc.backend.dto.user.UserDTO;
import es.codeurjc.backend.service.UserService;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
	
	@Autowired
	private UserService userService;
	
    @Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/me")
	public UserDTO me(HttpServletRequest request) {
		
		Principal principal = request.getUserPrincipal();
		
		if(principal != null) {
			return userService.getUserByUsername(principal.getName());
		} else {
			throw new NoSuchElementException();
		}
	}

	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@RequestBody NewUserDTO newUserDTO) throws SQLException {
		String password = null;
		if (newUserDTO.password() != null && !newUserDTO.password().isEmpty()) {
            password = passwordEncoder.encode(newUserDTO.password());
        }

		UserDTO userDTO = new UserDTO(null, newUserDTO.fullName(), newUserDTO.userName(), newUserDTO.phone(), newUserDTO.email(), 
			password, newUserDTO.age(), 0, "None", false, null, 
			List.of("USER"));

		userDTO = userService.createOrReplaceUser(null, userDTO);

		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(userDTO.id()).toUri();

		return ResponseEntity.created(location).body(userDTO);
	}

	@PutMapping("/me")
	public UserDTO replaceUser(HttpServletRequest request, @RequestBody NewUserDTO newUserDTO) throws SQLException {

		Principal principal = request.getUserPrincipal();
		
		if(principal != null) {
			String password = null;
			if (newUserDTO.password() != null && !newUserDTO.password().isEmpty()) {
				password = passwordEncoder.encode(newUserDTO.password());
			}

			UserDTO updatedUserDTO = new UserDTO(userService.getUserByUsername(principal.getName()).id(), newUserDTO.fullName(), 
				newUserDTO.userName(), newUserDTO.phone(), newUserDTO.email(), password, newUserDTO.age(), 
				userService.getUserByUsername(principal.getName()).numTicketsBought(), 
				userService.getUserByUsername(principal.getName()).favoriteGenre(), 
				userService.getUserByUsername(principal.getName()).image(), 
				userService.getUserByUsername(principal.getName()).tickets(), userService.getUserByUsername(principal.getName()).roles());
			
			return userService.createOrReplaceUser(userService.getUserByUsername(principal.getName()).id(), updatedUserDTO);
		} else {
			throw new NoSuchElementException();
		}
	}
}
