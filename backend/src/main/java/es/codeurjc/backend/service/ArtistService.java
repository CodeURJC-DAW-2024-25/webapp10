package es.codeurjc.backend.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.dto.artist.ArtistDTO;
import es.codeurjc.backend.dto.artist.ArtistMapper;
import es.codeurjc.backend.model.Artist;
import es.codeurjc.backend.repository.ArtistRepository;

@Service
public class ArtistService {

	@Autowired
	private ArtistRepository repository;

	@Autowired
	private ArtistMapper mapper;

	public Collection<ArtistDTO> getArtists() {
		return toDTOs(repository.findAll());
	}

	public ArtistDTO getArtist(Long id) {
		return toDTO(repository.findById(id).orElseThrow());
	}

	public ArtistDTO createArtist(ArtistDTO artistDTO) {
		
		if(artistDTO.id() != null) {
			throw new IllegalArgumentException();
		}

		Artist artist = toDomain(artistDTO);

		repository.save(artist);

		return toDTO(artist);
	}

	public ArtistDTO replaceArtist(Long id, ArtistDTO updatedArtistDTO) throws SQLException {
		
		if (repository.existsById(id)) {
			Artist updatedArtist = toDomain(updatedArtistDTO);
			updatedArtist.setId(id);
			repository.save(updatedArtist);
			return toDTO(updatedArtist);
		} else {
			throw new NoSuchElementException();
		}
	}

	public ArtistDTO createOrReplaceArtist(Long id, ArtistDTO artistDTO) throws SQLException {
		
		ArtistDTO artist;
		if (id == null) {
			artist = createArtist(artistDTO);
		} else {
			artist = replaceArtist(id, artistDTO);
		}
		return artist;
	}

	public ArtistDTO deleteArtist(Long id) {
		Artist artist = repository.findById(id).orElseThrow();

		ArtistDTO artistDTO = toDTO(artist);
		repository.deleteById(id);
		return artistDTO;
	}


	public boolean exist(Long id) {
		return repository.existsById(id);
	}


    public boolean existsName(String artistName) {
        return repository.findByArtistName(artistName).isPresent();
    }

	//-------------------------------------------DTO mapping methods-------------------------------------------
	private ArtistDTO toDTO(Artist artist) {
		return mapper.toDTO(artist);
	}

	private Artist toDomain(ArtistDTO artistDTO) {
		return mapper.toDomain(artistDTO);
	}

	private Collection<ArtistDTO> toDTOs(Collection<Artist> artists) {
		return mapper.toDTOs(artists);
	}
}