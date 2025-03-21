package es.codeurjc.backend.dto.concert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.codeurjc.backend.model.Concert;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ConcertMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "concertName", source = "concertName")
    @Mapping(target = "concertDetails", source = "concertDetails")
    @Mapping(target = "concertDate", source = "concertDate")
    @Mapping(target = "concertTime", source = "concertTime")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "stadiumPrice", source = "stadiumPrice")
    @Mapping(target = "trackPrice", source = "trackPrice")
    @Mapping(target = "map", source = "map")
    @Mapping(target = "concertImage", source = "concertImage")
    @Mapping(target = "color", source = "color")
    @Mapping(target = "artists", source = "artists")
    @Mapping(target = "ticketIds", source = "tickets")
    ConcertDTO toDTO(Concert concert);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "concertName", source = "concertName")
    @Mapping(target = "concertDetails", source = "concertDetails")
    @Mapping(target = "concertDate", source = "concertDate")
    @Mapping(target = "concertTime", source = "concertTime")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "stadiumPrice", source = "stadiumPrice")
    @Mapping(target = "trackPrice", source = "trackPrice")
    @Mapping(target = "map", source = "map")
    @Mapping(target = "concertImage", source = "concertImage")
    @Mapping(target = "color", source = "color")
    @Mapping(target = "artists", source = "artists")
    @Mapping(target = "tickets", source = "ticketIds")
    Concert toDomain(ConcertDTO concertDTO);

    List<ConcertDTO> toDTOs(Collection<Concert> concerts);
}