package es.codeurjc.backend.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.model.Ticket;
import es.codeurjc.backend.dto.TicketDTO;
import es.codeurjc.backend.dto.TicketMapper;
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

	public TicketDTO createTicket(TicketDTO TicketDTO) {

		if (TicketDTO.id() != null) {
			throw new IllegalArgumentException();
		}

		Ticket Ticket = toDomain(TicketDTO);

		repository.save(Ticket);

		return toDTO(Ticket);
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

	private Ticket toDomain(TicketDTO TicketDTO) {
		return mapper.toDomain(TicketDTO);
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
