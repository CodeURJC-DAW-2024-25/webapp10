package es.codeurjc.backend.dto.ticket;

public record NewTicketDTO(
    Long ticketId,
    String ticketType,
    Integer numTickets,
    Long concertId

    ) {}
    