package es.codeurjc.backend.dto;

import java.util.List;

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
    Boolean image,
    List<TicketDTO> tickets,
    List<String> roles
) {}

