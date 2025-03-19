package es.codeurjc.backend.dto;

public record NewTicketDTO(
    Long ticketId,
    String ticketType,
    Integer numTickets

    ) {}
