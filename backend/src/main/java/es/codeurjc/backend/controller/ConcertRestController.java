package es.codeurjc.backend.controller;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.backend.dto.concert.ConcertDTO;
import es.codeurjc.backend.service.ConcertService;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/v1/concerts")
public class ConcertRestController {

	@Autowired
    private ConcertService concertService;


	@GetMapping("/")
	public Page<ConcertDTO> getConcerts(Long userId, Pageable pageable) {
		return concertService.getConcerts(userId, pageable);
	}

	@GetMapping("/{id}")
	public ConcertDTO getConcert(@PathVariable long id) {
		return concertService.getConcert(id);
	}

	@PostMapping("/")
	public ResponseEntity<ConcertDTO> createConcert(@RequestBody ConcertDTO concertDTO) {

		concertDTO = concertService.createConcert(concertDTO);

		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(concertDTO.id()).toUri();

		return ResponseEntity.created(location).body(concertDTO);
	}

	@PutMapping("/{id}")
	public ConcertDTO replaceConcert(@PathVariable long id, @RequestBody ConcertDTO updatedConcertDTO) throws SQLException {

		return concertService.replaceConcert(id, updatedConcertDTO);
	}

	@DeleteMapping("/{id}")
	public ConcertDTO deleteConcert(@PathVariable long id) {

		return concertService.deleteConcert(id);
	}

	@PostMapping("/{id}/image")
	public ResponseEntity<Object> createConcertImage(@PathVariable long id, @RequestParam MultipartFile imageFile)
			throws IOException {

		concertService.createConcertImage(id, imageFile.getInputStream(), imageFile.getSize());

		URI location = fromCurrentRequest().build().toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{id}/image")
	public ResponseEntity<Object> getConcertImage(@PathVariable long id) throws SQLException, IOException {

		Resource image = concertService.getConcertImage(id);

		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
				.body(image);

	}

	@PutMapping("/{id}/image")
	public ResponseEntity<Object> replaceConcertImage(@PathVariable long id, @RequestParam MultipartFile imageFile)
			throws IOException {

		concertService.replaceConcertImage(id, imageFile.getInputStream(), imageFile.getSize());

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/image")
	public ResponseEntity<Object> deleteConcertImage(@PathVariable long id) throws IOException {

		concertService.deleteConcertImage(id);

		return ResponseEntity.noContent().build();
	}
}