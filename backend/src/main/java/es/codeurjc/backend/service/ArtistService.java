package es.codeurjc.backend.service;

import java.sql.SQLException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.dto.ArtistDTO;
import es.codeurjc.backend.dto.ArtistMapper;
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

	public ArtistDTO getArtist(long id) {
		return toDTO(repository.findById(id).orElseThrow());
	}

	public boolean exist(long id) {
		return repository.existsById(id);
	}

	public ArtistDTO deleteArtist(long id) {

		Artist artist = repository.findById(id).orElseThrow();
		ArtistDTO artistDTO = toDTO(artist);

		repository.deleteById(id);

		return artistDTO;
	}

	public ArtistDTO createArtist(ArtistDTO artistDTO) {

		if (artistDTO.id() != null) {
			throw new IllegalArgumentException();
		}

		Artist artist = toDomain(artistDTO);

		repository.save(artist);

		return toDTO(artist);
	}

	public ArtistDTO replaceArtist(long id, ArtistDTO updateArtistDTO) throws SQLException {

		Artist oldArtist = repository.findById(id).orElseThrow();
		Artist updatedArtist = toDomain(updateArtistDTO);
		updatedArtist.setId(id);
		repository.save(updatedArtist);
		return toDTO(updatedArtist);

	}

	public boolean existsName(String artistName) {
		return repository.findByArtistName(artistName).isPresent();
	}

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
