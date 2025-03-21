package es.codeurjc.backend.dto.artist;

public record NewArtistRequestDTO (
    String artistName,
    String musicalStyle,
    String artistInfo){
}