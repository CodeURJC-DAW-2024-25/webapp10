package es.codeurjc.backend.dto.artist;
import jakarta.validation.constraints.NotBlank;

public record NewArtistRequestDTO (
    @NotBlank(message = "Artist name cannot be empty")
    String artistName,
    @NotBlank(message = "Musical style cannot be empty")
    String musicalStyle,
    @NotBlank(message = "Artist info cannot be empty")
    String artistInfo){
}