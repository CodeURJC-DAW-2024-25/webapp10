package es.codeurjc.backend.dto.concert;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record NewConcertDTO(
    @NotBlank(message = "Concert name cannot be empty")
    String concertName,
    @NotBlank(message = "Concert details cannot be empty")
    String concertDetails,
    @NotBlank(message = "Concert date cannot be empty")
    String concertDate,
    @NotBlank(message = "Concert time cannot be empty")
    String concertTime,
    @NotBlank(message = "Location cannot be empty")
    String location,
    @NotNull(message = "Stadium price cannot be empty")
    Integer stadiumPrice,
    @NotNull(message = "Track price cannot be empty")
    Integer trackPrice,
    String map,
    @NotNull(message = "At least one artist must be selected")
    List<Long> artistIds,
    MultipartFile imageFile
) {}
