package es.codeurjc.backend.dto;

public record ArtistDTO(
    Long id, 
    String artistName,
    String musicalStyle,
    String artistInfo
    ) {}