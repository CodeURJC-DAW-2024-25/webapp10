package es.codeurjc.backend.service;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Collection;
import java.util.NoSuchElementException;


import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import es.codeurjc.backend.dto.concert.ConcertDTO;
import es.codeurjc.backend.dto.concert.ConcertMapper;
import es.codeurjc.backend.model.Concert;

import es.codeurjc.backend.repository.ConcertRepository;

@Service
public class ConcertService {

	@Autowired
	private ConcertRepository repository;

	@Autowired
	private ConcertMapper mapper;

	public boolean exist(long id) {
		return repository.existsById(id);
	}

	public Page<ConcertDTO> getConcerts(Long concertId, Pageable pageable) {
        Page<Concert> concerts;
        if (concertId == null) {
            concerts = repository.findAll(pageable);
        } else {
            concerts = repository.findConcertsByUserPreference(concertId, pageable);
        }
        return concerts.map(this::toDTO);
    }

 /*    public Page<ConcertDTO> getConcertsPaginated(int page) {
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable).map(this::toDTO);
    } */

	public ConcertDTO getConcert(long id) {
		return toDTO(repository.findById(id).orElseThrow());
	}

	public Collection<ConcertDTO> getAllConcert() {
		return toDTOs(repository.findAll());
	}

	public ConcertDTO deleteConcert(long id) {

		Concert concert = repository.findById(id).orElseThrow();
		ConcertDTO concertDTO = toDTO(concert);

		repository.deleteById(id);

		return concertDTO;
	}

	public ConcertDTO createConcert(ConcertDTO concertDTO) {

		if (concertDTO == null ||concertDTO.id() != null) {
			throw new IllegalArgumentException();
		}

		Concert concert = toDomain(concertDTO);

		repository.save(concert);

		return toDTO(concert);
	}

	public ConcertDTO replaceConcert(long id, ConcertDTO updateConcertDTO) throws SQLException {

		if (repository.existsById(id)) {
			Concert updatedConcert = toDomain(updateConcertDTO);
			updatedConcert.setId(id);
			repository.save(updatedConcert);
			return toDTO(updatedConcert);
		} else {
			throw new NoSuchElementException();
		}

	}

	public Resource getConcertImage(long id) throws SQLException {

		Concert concert = repository.findById(id).orElseThrow();

		if (concert.getImageFile() != null) {
			return new InputStreamResource(concert.getImageFile().getBinaryStream());
		} else {
			throw new NoSuchElementException();
		}
	}

	public void createConcertImage(long id, InputStream inputStream, long size) {

		Concert concert = repository.findById(id).orElseThrow();

		concert.setConcertImage(true);
		concert.setImageFile(BlobProxy.generateProxy(inputStream, size));

		repository.save(concert);
	}

	public void replaceConcertImage(long id, InputStream inputStream, long size) {

		Concert concert = repository.findById(id).orElseThrow();

		if (!concert.getConcertImage()) {
			throw new NoSuchElementException();
		}

		concert.setImageFile(BlobProxy.generateProxy(inputStream, size));

		repository.save(concert);
	}

	public void deleteConcertImage(long id) {

		Concert concert = repository.findById(id).orElseThrow();

		if (!concert.getConcertImage()) {
			throw new NoSuchElementException();
		}

		concert.setImageFile(null);
		concert.setConcertImage(false);

		repository.save(concert);
	}

	public ConcertDTO createOrReplaceConcert(Long id, ConcertDTO concertDTO) throws SQLException {
		
		ConcertDTO concert;
		if(id == null) {
			concert = createConcert(concertDTO);
		} else {
			concert = replaceConcert(id, concertDTO);
		}
		return concert;
	}

	private ConcertDTO toDTO(Concert concert) {
		return mapper.toDTO(concert);
	}

	private Concert toDomain(ConcertDTO ConcertDTO) {
		return mapper.toDomain(ConcertDTO);
	}

	private Collection<ConcertDTO> toDTOs(Collection<Concert> concerts) {
		return mapper.toDTOs(concerts);
	}

}
