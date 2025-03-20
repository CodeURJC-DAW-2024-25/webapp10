package es.codeurjc.backend.dto;

public record NewArtistRequestDTO (
    String artistName,
    String musicalStyle,
    String artistInfo){
}