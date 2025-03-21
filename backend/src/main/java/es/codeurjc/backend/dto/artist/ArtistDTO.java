package es.codeurjc.backend.dto.artist;

public record ArtistDTO(
    Long id, 
    String artistName,
    String musicalStyle,
    String artistInfo
    ) {}