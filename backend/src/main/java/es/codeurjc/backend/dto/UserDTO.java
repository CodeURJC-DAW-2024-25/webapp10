package es.codeurjc.backend.dto;

import java.util.List;

public record UserDTO(    
    Long id,
	String fullName,
    String userName,
    Integer phone,
    String email,
    Integer age,
    Integer numTicketsBought,
    String favoriteGenre,
    boolean image,
    List<TicketDTO> tickets,
	List<String> roles
    
    ) {}

