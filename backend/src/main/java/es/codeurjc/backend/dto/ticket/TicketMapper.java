package es.codeurjc.backend.dto.ticket;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.codeurjc.backend.model.Ticket;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "ticketType", source = "ticketType")
    @Mapping(target = "prices", source = "prices")
    @Mapping(target = "userOwnerId", source = "userOwner.id")
    @Mapping(target = "numTickets", source = "numTickets")
    @Mapping(target = "concertId", source = "concert.id")
    TicketDTO toDTO(Ticket ticket);

    List<TicketDTO> toDTOs(Collection<Ticket> tickets);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "ticketType", source = "ticketType")
    @Mapping(target = "prices", source = "prices")
    @Mapping(target = "userOwner.id", source = "userOwnerId")
    @Mapping(target = "numTickets", source = "numTickets")
    @Mapping(target = "concert.id", source = "concertId")
    Ticket toDomain(TicketDTO ticketDTO);
}
