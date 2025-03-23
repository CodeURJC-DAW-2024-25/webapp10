package es.codeurjc.backend.controller;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.backend.dto.user.NewUserDTO;
import es.codeurjc.backend.dto.user.UserAnswerDTO;
import es.codeurjc.backend.dto.user.UserDTO;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.UserService;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {
	
	@Autowired
	private UserService userService;
	
    @Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/me")
	public UserAnswerDTO me(HttpServletRequest request) {
		
		Principal principal = request.getUserPrincipal();
		
		if(principal != null) {
			UserDTO userDTO = userService.getUserByUsername(principal.getName());
			return new UserAnswerDTO(userDTO.id(), userDTO.fullName(), userDTO.userName(), userDTO.phone(), userDTO.email(),
				userDTO.age(), userDTO.numTicketsBought(), userDTO.favoriteGenre(), userDTO.image(), userDTO.tickets(), userDTO.roles());
		} else {
			throw new NoSuchElementException();
		}
	}

	@PostMapping("/")
	public ResponseEntity<UserAnswerDTO> createUser(@RequestBody NewUserDTO newUserDTO) throws SQLException {
		String password = null;
		if (newUserDTO.password() != null && !newUserDTO.password().isEmpty()) {
            password = passwordEncoder.encode(newUserDTO.password());
        }

		UserDTO userDTO = new UserDTO(null, newUserDTO.fullName(), newUserDTO.userName(), newUserDTO.phone(), newUserDTO.email(), 
			password, newUserDTO.age(), 0, "None", false, null, 
			List.of("USER"));

		userDTO = userService.createOrReplaceUser(null, userDTO);

		UserAnswerDTO userAnswerDTO = new UserAnswerDTO(userDTO.id(), userDTO.fullName(), userDTO.userName(), userDTO.phone(), userDTO.email(),
			userDTO.age(), userDTO.numTicketsBought(), userDTO.favoriteGenre(), userDTO.image(), userDTO.tickets(), userDTO.roles());

		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(userAnswerDTO.id()).toUri();

		return ResponseEntity.created(location).body(userAnswerDTO);
	}

	@PutMapping("/me")
	public UserAnswerDTO replaceUser(HttpServletRequest request, @RequestBody NewUserDTO newUserDTO) throws SQLException {

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
			
			UserDTO userDTO = userService.createOrReplaceUser(userService.getUserByUsername(principal.getName()).id(), updatedUserDTO);
			return new UserAnswerDTO(userDTO.id(), userDTO.fullName(), userDTO.userName(), userDTO.phone(), userDTO.email(),
				userDTO.age(), userDTO.numTicketsBought(), userDTO.favoriteGenre(), userDTO.image(), userDTO.tickets(), userDTO.roles());
		} else {
			throw new NoSuchElementException();
		}
	}

	@PostMapping("/me/image")
	public ResponseEntity<Object> createUserImage(HttpServletRequest request, @RequestParam MultipartFile imageFile)
			throws IOException {
			
		Principal principal = request.getUserPrincipal();

		if(principal != null) {

			userService.createUserImage(userService.getUserByUsername(principal.getName()).id(), imageFile.getInputStream(), imageFile.getSize());

			URI location = fromCurrentRequest().build().toUri();

			return ResponseEntity.created(location).build();
		} else {
			throw new NoSuchElementException();
		}

		
	}

	@GetMapping("/me/image")
	public ResponseEntity<Object> getUserImage(HttpServletRequest request) throws SQLException, IOException {

		Principal principal = request.getUserPrincipal();

		if(principal != null) {

			Resource image = userService.getUserImage(userService.getUserByUsername(principal.getName()).id());

			return ResponseEntity
					.ok()
					.header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
					.body(image);
		} else {
			throw new NoSuchElementException();
		}
	}

	@PutMapping("/me/image")
	public ResponseEntity<Object> replaceUserImage(HttpServletRequest request, @RequestParam MultipartFile imageFile)
			throws IOException {

		Principal principal = request.getUserPrincipal();

		if(principal != null) {

			userService.replaceUserImage(userService.getUserByUsername(principal.getName()).id(), imageFile.getInputStream(), imageFile.getSize());

			return ResponseEntity.noContent().build();
		} else {
			throw new NoSuchElementException();
		}
	}

	@DeleteMapping("/me/image")
	public ResponseEntity<Object> deleteUserImage(HttpServletRequest request) throws IOException {

		Principal principal = request.getUserPrincipal();

		if(principal != null) {

			userService.deleteUserImage(userService.getUserByUsername(principal.getName()).id());

		return ResponseEntity.noContent().build();
		} else {
			throw new NoSuchElementException();
		}

		
	}
}
