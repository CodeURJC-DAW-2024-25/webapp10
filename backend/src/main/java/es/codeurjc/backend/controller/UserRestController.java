package es.codeurjc.backend.controller;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;
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

    @Autowired
    private TicketService ticketService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/currentUser")
public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
    if (principal != null) {
        UserDTO userDTO = userService.getUserByUsername(principal.getName());
        return ResponseEntity.ok(userDTO);
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

    @GetMapping("/{id}")
    public UserAnswerDTO getUser(@PathVariable Long id, HttpServletRequest request) {
        validateAuthenticatedUser(id, request);
        UserDTO userDTO = userService.getUser(id);
        return toUserAnswerDTO(userDTO);
    }

    @PutMapping("/{id}")
    public UserAnswerDTO replaceUser(@PathVariable Long id, @RequestBody NewUserDTO newUserDTO, HttpServletRequest request) throws SQLException, IOException {
        validateAuthenticatedUser(id, request);
        UserDTO userDTO = userService.UserCreationReplacement(id, newUserDTO, null, passwordEncoder);
        return toUserAnswerDTO(userDTO);
    }

    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<TicketDTO>> getUserTickets(@PathVariable Long id, HttpServletRequest request) {
        validateAuthenticatedUser(id, request);

        List<TicketDTO> userTickets = ticketService.getTickets().stream()
            .filter(ticket -> ticket.userOwnerId().equals(id))
            .toList();

        return ResponseEntity.ok(userTickets);
    }

    @GetMapping("/{id}/tickets/{ticketId}")
    public ResponseEntity<TicketDTO> getUserTicketById(@PathVariable Long id, @PathVariable Long ticketId, HttpServletRequest request) {
        validateAuthenticatedUser(id, request);

        TicketDTO ticket = ticketService.getTicket(ticketId);

        if (ticket.userOwnerId().equals(id)) {
            return ResponseEntity.ok(ticket);
        } else {
            return ResponseEntity.status(403).body(null);
        }
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<Object> createUserImage(@PathVariable Long id, @RequestParam MultipartFile imageFile, HttpServletRequest request) throws IOException {
        validateAuthenticatedUser(id, request);
        userService.createUserImage(id, imageFile.getInputStream(), imageFile.getSize());

        URI location = fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<Object> getUserImage(@PathVariable Long id, HttpServletRequest request) throws SQLException, IOException {
        validateAuthenticatedUser(id, request);
        Resource image = userService.getUserImage(id);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").body(image);
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<Object> replaceUserImage(@PathVariable Long id, @RequestParam MultipartFile imageFile, HttpServletRequest request) throws IOException {
        validateAuthenticatedUser(id, request);
        userService.replaceUserImage(id, imageFile.getInputStream(), imageFile.getSize());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/image")
    public ResponseEntity<Object> deleteUserImage(@PathVariable Long id, HttpServletRequest request) throws IOException {
        validateAuthenticatedUser(id, request);
        userService.deleteUserImage(id);

        return ResponseEntity.noContent().build();
    }

    private void validateAuthenticatedUser(Long id, HttpServletRequest request) {
        UserDTO authenticatedUser = userService.getAuthenticatedUser(request);
        if (!authenticatedUser.id().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to access this resource.");
        }
    }

    private UserAnswerDTO toUserAnswerDTO(UserDTO userDTO) {
        return new UserAnswerDTO(userDTO.id(), userDTO.fullName(), userDTO.userName(), userDTO.phone(),
                userDTO.email(), userDTO.age(), userDTO.numTicketsBought(), userDTO.favoriteGenre(),
                userDTO.image(), userDTO.tickets(), userDTO.roles());
    }
}
