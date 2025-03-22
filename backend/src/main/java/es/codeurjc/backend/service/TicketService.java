package es.codeurjc.backend.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.model.Ticket;
import es.codeurjc.backend.dto.ticket.TicketDTO;
import es.codeurjc.backend.dto.ticket.TicketMapper;
import es.codeurjc.backend.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	private TicketRepository repository;

	@Autowired
	private TicketMapper mapper;

	public Collection<TicketDTO> getTickets() {
		return toDTOs(repository.findAll());
	}

	public TicketDTO getTicket(long id) {
		return toDTO(repository.findById(id).orElseThrow());
	}

	public TicketDTO deleteTicket(long id) {

		Ticket Ticket = repository.findById(id).orElseThrow();
		TicketDTO TicketDTO = toDTO(Ticket);

		repository.deleteById(id);

		return TicketDTO;
	}

	public TicketDTO createTicket(TicketDTO ticketDTO) {

		if (ticketDTO == null ||ticketDTO.id() != null) {
			throw new IllegalArgumentException();
		}
		Ticket ticket = toDomain(ticketDTO);
		repository.save(ticket);

		return toDTO(ticket);
	}

	public TicketDTO replaceTicket(long id, TicketDTO updateTicketDTO) throws SQLException {

		if (repository.existsById(id)) {
			Ticket updatedTicket = toDomain(updateTicketDTO);
			updatedTicket.setId(id);
			repository.save(updatedTicket);
			return toDTO(updatedTicket);
		} else {
			throw new NoSuchElementException();
		}

	}

	private TicketDTO toDTO(Ticket Ticket) {
		return mapper.toDTO(Ticket);
	}

	private Ticket toDomain(TicketDTO ticketDTO) {
		return mapper.toDomain(ticketDTO);
	}

	private Collection<TicketDTO> toDTOs(Collection<Ticket> Tickets) {
		return mapper.toDTOs(Tickets);
	}

	public TicketDTO createOrReplaceTicket(Long id, TicketDTO ticketDTO) throws SQLException {
		
		TicketDTO ticket;
		if(id == null) {
			ticket = createTicket(ticketDTO);
		} else {
			ticket = replaceTicket(id, ticketDTO);
		}
		return ticket;
	}

}
