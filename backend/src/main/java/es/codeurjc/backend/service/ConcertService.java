package es.codeurjc.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.model.Concert;
import es.codeurjc.backend.repository.ConcertRepository;

@Service
public class ConcertService {

    @Autowired
	private ConcertRepository repository;

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
}
