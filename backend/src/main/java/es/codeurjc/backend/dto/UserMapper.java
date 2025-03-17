package es.codeurjc.backend.dto;

import org.mapstruct.Mapper;

import es.codeurjc.backend.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);
    
}