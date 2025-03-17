package es.codeurjc.backend.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.codeurjc.backend.model.Concert;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ConcertMapper {

    ConcertDTO toDTO(Concert concert);

    List<ConcertDTO> toDTOs(Collection<Concert> concerts);

    @Mapping(target = "imageFile", ignore = true)
    @Mapping(target = "artists", ignore = true) 
    @Mapping(target = "tickets", ignore = true) 
    Concert toDomain(ConcertDTO concertDTO);

}
