package es.codeurjc.backend.controller;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;

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
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import es.codeurjc.backend.dto.ticket.TicketDTO;
import es.codeurjc.backend.dto.user.NewUserDTO;
import es.codeurjc.backend.dto.user.UserAnswerDTO;
import es.codeurjc.backend.dto.user.UserDTO;
import es.codeurjc.backend.service.TicketService;
import es.codeurjc.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

	@Autowired
	private UserService userService;
	private TicketService ticketService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/me")
	public UserAnswerDTO me(HttpServletRequest request) {

		UserDTO userDTO = userService.getAuthenticatedUser(request);
		return toUserAnswerDTO(userDTO);
	}

	@PostMapping("/")
	public ResponseEntity<UserAnswerDTO> createUser(@RequestBody NewUserDTO newUserDTO) throws SQLException, IOException {
		UserDTO userDTO = userService.UserCreationReplacement(null, newUserDTO, null, passwordEncoder);

		UserAnswerDTO userAnswerDTO = toUserAnswerDTO(userDTO);

		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(userAnswerDTO.id()).toUri();
		return ResponseEntity.created(location).body(userAnswerDTO);
	}

	@PutMapping("/me")
	public UserAnswerDTO replaceUser(HttpServletRequest request, @RequestBody NewUserDTO newUserDTO) throws SQLException, IOException {
		Long userId = userService.getAuthenticatedUser(request).id();
		UserDTO userDTO = userService.UserCreationReplacement(userId, newUserDTO, null, passwordEncoder);

		return toUserAnswerDTO(userDTO);
	}

	@GetMapping("/me/tickets")
	public ResponseEntity<List<TicketDTO>> getUserTickets(HttpServletRequest request) {

		Long userId = userService.getAuthenticatedUser(request).id();

		List<TicketDTO> userTickets = ticketService.getTickets().stream()
			.filter(ticket -> ticket.userOwnerId().equals(userId))
			.toList();

		return ResponseEntity.ok(userTickets);
	}

	@GetMapping("/me/tickets/{ticketId}")
	public ResponseEntity<TicketDTO> getUserTicketById(HttpServletRequest request, @PathVariable Long ticketId) {
		

		Long userId = userService.getAuthenticatedUser(request).id();

		TicketDTO ticket = ticketService.getTicket(ticketId);

		if (ticket.userOwnerId().equals(userId)) {
			return ResponseEntity.ok(ticket);
		} else {
			return ResponseEntity.status(403).body(null); 
		}
	}

	@PostMapping("/me/image")
	public ResponseEntity<Object> createUserImage(HttpServletRequest request, @RequestParam MultipartFile imageFile)
			throws IOException {
		UserDTO currentUser = userService.getAuthenticatedUser(request);
		userService.createUserImage(currentUser.id(), imageFile.getInputStream(), imageFile.getSize());

		URI location = fromCurrentRequest().build().toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/me/image")
	public ResponseEntity<Object> getUserImage(HttpServletRequest request) throws SQLException, IOException {
		UserDTO currentUser = userService.getAuthenticatedUser(request);
		Resource image = userService.getUserImage(currentUser.id());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").body(image);
	}

	@PutMapping("/me/image")
	public ResponseEntity<Object> replaceUserImage(HttpServletRequest request, @RequestParam MultipartFile imageFile)
			throws IOException {
		UserDTO currentUser = userService.getAuthenticatedUser(request);
		userService.replaceUserImage(currentUser.id(), imageFile.getInputStream(), imageFile.getSize());

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/me/image")
	public ResponseEntity<Object> deleteUserImage(HttpServletRequest request) throws IOException {
		UserDTO currentUser = userService.getAuthenticatedUser(request);
		userService.deleteUserImage(currentUser.id());

		return ResponseEntity.noContent().build();
	}

	private UserAnswerDTO toUserAnswerDTO(UserDTO userDTO) {
		return new UserAnswerDTO(userDTO.id(), userDTO.fullName(), userDTO.userName(), userDTO.phone(),
				userDTO.email(), userDTO.age(), userDTO.numTicketsBought(), userDTO.favoriteGenre(),
				userDTO.image(), userDTO.tickets(), userDTO.roles());
	}
}
