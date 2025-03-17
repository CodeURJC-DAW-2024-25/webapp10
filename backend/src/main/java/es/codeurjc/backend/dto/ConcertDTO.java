package es.codeurjc.backend.dto;

import java.util.List;

public record ConcertDTO(
    Long id,
    String concertName,
    String concertDetails,
    String concertDate,
    String concertTime,
    String location,
    String color,
    Integer stadiumPrice,
    Integer trackPrice,
    String map,
    boolean concertImage,
    List<Long> artistIds,
    List<Long> ticketIds
    
    ) {}
