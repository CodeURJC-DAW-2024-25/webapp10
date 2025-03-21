package es.codeurjc.backend.dto.artist;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.codeurjc.backend.model.Artist;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    @Mapping(target = "artistName", source = "artistName")
    @Mapping(target = "musicalStyle", source = "musicalStyle")
    @Mapping(target = "artistInfo", source = "artistInfo")
    ArtistDTO toDTO(Artist artist);

    List<ArtistDTO> toDTOs(Collection<Artist> artists);

    @Mapping(target = "artistName", source = "artistName")
    @Mapping(target = "musicalStyle", source = "musicalStyle")
    @Mapping(target = "artistInfo", source = "artistInfo")
    @Mapping(target = "concerts", ignore = true)
    Artist toDomain(ArtistDTO artistDTO);

}