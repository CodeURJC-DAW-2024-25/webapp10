package es.codeurjc.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import es.codeurjc.backend.model.Artist;
import es.codeurjc.backend.model.Concert;
import es.codeurjc.backend.model.User;
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

	public List<Concert> getConcerts(int offset, int limit, User user) {
		Pageable pageable = PageRequest.of(offset / limit, limit);
		List<Concert> allConcerts = repository.findAll();

		if (user != null) {
			String favoriteGenre = user.getFavoriteGenre();
			Map<Concert, Integer> genreCount = new HashMap<>();
			for (Concert concert : allConcerts) {
				int count = 0;
				for (Artist artist : concert.getArtists()) {
					if (artist.getMusicalStyle().equals(favoriteGenre)) {
						count++;
					}
				}
				if (count >= 0) {
					genreCount.put(concert, count);
				}

			}
			List<Concert> sortedConcerts = genreCount.entrySet().stream()
					.sorted(Map.Entry.<Concert, Integer>comparingByValue().reversed())
					.map(Map.Entry::getKey)
					.collect(Collectors.toList());

			return sortedConcerts.stream()
					.skip(offset)
					.limit(limit)
					.collect(Collectors.toList());

		}
		Page<Concert> concertPage = repository.findAll(pageable);
		return concertPage.getContent();

	}

	public Page<Concert> getConcertsPaginated(int page) {
		int size = 10;
		Pageable pageable = PageRequest.of(page, size);
		return repository.findAll(pageable);
	}

	public List<Concert> findAllConcerts() {
		return repository.findAll();
	}
}
