package es.codeurjc.backend.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.codeurjc.backend.model.Ticket;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    TicketDTO toDTO(Ticket ticket);

    @Mapping(target = "userOwnerId", source = "userOwner.id")
    @Mapping(target = "concertId", source = "concert.id")

    List<TicketDTO> toDTOs(Collection<Ticket> tickets);
    Ticket toDomain(TicketDTO TicketDTO);

}
