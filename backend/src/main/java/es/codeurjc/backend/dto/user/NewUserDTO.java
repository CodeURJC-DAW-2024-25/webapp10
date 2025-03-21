package es.codeurjc.backend.dto.user;

import org.springframework.web.multipart.MultipartFile;

public record NewUserDTO(    
	String fullName,
    String userName,
    Integer phone,
    String password,
    String email,
    Integer age,
    MultipartFile profilePhoto
    
    ) {}
