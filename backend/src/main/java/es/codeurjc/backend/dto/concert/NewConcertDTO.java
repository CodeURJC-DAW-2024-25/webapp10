package es.codeurjc.backend.dto.concert;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

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

    @Min(1)
    @Positive(message = "Stadium price must be greater than 0")
    Integer stadiumPrice,

    @Min(1)
    @Positive(message = "Track price must be greater than 0")
    Integer trackPrice,
    
    String map,

    @NotEmpty(message = "At least one artist must be selected")
    List<Long> artistIds,

    MultipartFile imageFile
) {}
