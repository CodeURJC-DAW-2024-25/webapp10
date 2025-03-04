package es.codeurjc.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.model.Artist;
import es.codeurjc.backend.repository.ArtistRepository;

@Service
public class ArtistService {
    
    @Autowired
	private ArtistRepository repository;

	public Optional<Artist> findById(long id) {
		return repository.findById(id);
	}
	
	public boolean exist(long id) {
		return repository.existsById(id);
	}

	public List<Artist> findAll() {
		return repository.findAll();
	}

	public void save(Artist artist) {
		repository.save(artist);
	}

	public void delete(long id) {
		repository.deleteById(id);
	}

    public boolean existsName(String artistName) {
        return repository.findByArtistName(artistName).isPresent();
    }

	public Artist getArtistById(long id) {
		return repository.findById(id)
			.orElseThrow(() -> new RuntimeException("Artist not found with id: " + id));
	}

	public void updateArtist(Long id, Artist updatedArtist) {
		Artist existingArtist = getArtistById(id);
		if (updatedArtist.getArtistName() != null) {
			existingArtist.setArtistName(updatedArtist.getArtistName());
		}
		if (updatedArtist.getMusicalStyle() != null) {
			existingArtist.setMusicalStyle(updatedArtist.getMusicalStyle());
		}
		if (updatedArtist.getArtistInfo() != null) {
			existingArtist.setArtistInfo(updatedArtist.getArtistInfo());
		}
		repository.save(existingArtist);
	}
	
}
