package es.codeurjc.backend.dto;

import java.util.List;

public record ArtistDTO(
    Long id, 
    String artistName,
    String musicalStyle,
    String artistInfo,
    List<Long> concertsIds
    
    ) {}