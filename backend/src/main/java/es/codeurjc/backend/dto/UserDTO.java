package es.codeurjc.backend.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public record UserDTO(    
    Long id,
	String fullName,
    String userName,
    Integer phone,
    String email,
    String password,
    Integer age,
    Integer numTicketsBought,
    String favoriteGenre,
    MultipartFile profilePhoto,
    List<TicketDTO> tickets,
	List<String> roles
    
    ) {}

