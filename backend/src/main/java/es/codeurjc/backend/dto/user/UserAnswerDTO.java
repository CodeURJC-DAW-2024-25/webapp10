package es.codeurjc.backend.dto.user;

import java.util.List;

import es.codeurjc.backend.dto.ticket.TicketDTO;

public record UserAnswerDTO(
    Long id,
    String fullName,
    String userName,
    Integer phone,
    String email,
    Integer age,
    Integer numTicketsBought,
    String favoriteGenre,
    Boolean image,
    List<TicketDTO> tickets,
    List<String> roles
) {}
