package es.codeurjc.backend.dto.concert;

import java.util.List;

import es.codeurjc.backend.dto.artist.ArtistDTO;
import es.codeurjc.backend.dto.ticket.TicketDTO;

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
    Boolean concertImage,
    String color,
    List<ArtistDTO> artists,
    List<TicketDTO> tickets
    
    ) {}
