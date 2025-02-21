package es.codeurjc.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.model.Ticket;
import es.codeurjc.backend.repository.TicketRepository;

@Service
public class TicketService {
    
    @Autowired
	private TicketRepository repository;

	public Optional<Ticket> findById(long id) {
		return repository.findById(id);
	}
	
	public boolean exist(long id) {
		return repository.existsById(id);
	}

	public List<Ticket> findAll() {
		return repository.findAll();
	}

	public void save(Ticket ticket) {
		repository.save(ticket);
	}

	public void delete(long id) {
		repository.deleteById(id);
	}
}
