package es.codeurjc.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import es.codeurjc.backend.model.Concert;
import es.codeurjc.backend.repository.ConcertRepository;

@Service
public class ConcertService {

	@Autowired
	private ConcertRepository repository;

	public List<Concert> getConcerts() {
		return repository.findAll();
	}

	public Optional<Concert> findById(long id) {
		return repository.findById(id);
	}

	public boolean exist(long id) {
		return repository.existsById(id);
	}

	public List<Concert> findAll() {
		return repository.findAll();
	}

	public void save(Concert concert) {
		repository.save(concert);
	}

	public void delete(long id) {
		repository.deleteById(id);
	}

	public void initializeConcerts() {
		for (int i = 1; i <= 20; i++) {
			Concert concert = new Concert();
			concert.setConcertName("Concierto " + i);
			save(concert);
		}
	}
}
