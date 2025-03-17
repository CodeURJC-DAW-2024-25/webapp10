package es.codeurjc.daw.library.controller;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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

import es.codeurjc.backend.dto.ArtistDTO;
import es.codeurjc.backend.dto.ConcertDTO;
import es.codeurjc.backend.service.ArtistService;
import es.codeurjc.backend.service.ConcertService;
import es.codeurjc.backend.service.TicketService;
import es.codeurjc.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/concerts")
public class RestController {

	@Autowired
	private ArtistService artistService;
    private ConcertService concertService;
    private TicketService ticketService;
    private UserService userService;


	@GetMapping("/")
	public Collection<ConcertDTO> getConcerts() {

		return concertService.getConcerts();
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
	public ConcertDTO replaceConcert(@PathVariable long id, @RequestBody BookDTO updatedBookDTO) throws SQLException {

		return concertService.replaceConcert(id, updatedBookDTO);
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

		Resource postImage = concertService.getConcertImage(id);

		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
				.body(postImage);

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