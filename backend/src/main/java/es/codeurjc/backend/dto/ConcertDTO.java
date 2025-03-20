package es.codeurjc.backend.dto;

import java.util.List;

public record ConcertDTO(
    Long id,
    String concertName,
    String concertDetails,
    String concertDate,
    String concertTime,
    String location,
    Integer stadiumPrice,
    Integer trackPrice,
    String map,
    boolean concertImage,
    String color,
    List<ArtistDTO> artistIds,
    List<TicketDTO> ticketIds
    
    ) {}
