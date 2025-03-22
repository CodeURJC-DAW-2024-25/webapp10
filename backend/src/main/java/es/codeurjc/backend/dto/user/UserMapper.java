package es.codeurjc.backend.dto.user;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import es.codeurjc.backend.dto.ticket.TicketMapper;
import es.codeurjc.backend.model.User;

@Mapper(componentModel = "spring",uses = { TicketMapper.class}, 
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "encodedPassword")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "numTicketsBought", source = "numTicketsBought")
    @Mapping(target = "favoriteGenre", source = "favoriteGenre")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "tickets", source = "tickets")
    @Mapping(target = "roles", source = "roles")
    UserDTO toDTO(User user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "encodedPassword", source = "password")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "numTicketsBought", source = "numTicketsBought")
    @Mapping(target = "favoriteGenre", source = "favoriteGenre")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "tickets", source = "tickets")
    @Mapping(target = "roles", source = "roles")
    User toDomain(UserDTO userDTO);

    List<UserDTO> toDTOs(Collection<User> users);

}