package es.codeurjc.backend.dto.ticket;

public record TicketDTO(
    Long id,
    String ticketType,
    Integer prices,
    Long userOwnerId,
    Integer numTickets,
    Long concertId
    
    ) {}