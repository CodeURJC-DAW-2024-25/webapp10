package es.codeurjc.backend.dto.user;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record NewUserDTO(
    @NotBlank(message = "Full name cannot be empty.") 
    String fullName,

    @NotBlank(message = "Username cannot be empty.") 
    String userName,

    @NotBlank(message = "Phone number must have 9 digits.") 
    Integer phone,

    @NotBlank(message = "Password cannot be empty.") 
    String password,
    
    @Email(message = "Please enter a valid email.") String email,

    @NotBlank(message = "Age cannot be empty.") 
    int age,
    
    MultipartFile profilePhoto
) {}
