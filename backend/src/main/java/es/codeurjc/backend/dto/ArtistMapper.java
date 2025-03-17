package es.codeurjc.backend.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.codeurjc.backend.model.Artist;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    ArtistDTO toDTO(Artist artist);

    List<ArtistDTO> toDTOs(Collection<Artist> artists);

    @Mapping(target = "concerts", ignore = true) 
    Artist toDomain(ArtistDTO artistDTO);

}