package es.codeurjc.backend.controller;

import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.backend.dto.artist.ArtistDTO;
import es.codeurjc.backend.service.ArtistService;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/v1/artists")
public class ArtistRestController {

    @Autowired
    private ArtistService artistService;

    @GetMapping("/")
    public Collection<ArtistDTO> getArtists() {

        return artistService.getArtists();
    }

    @GetMapping("/{id}")
    public ArtistDTO getArtist(@PathVariable long id) {

        return artistService.getArtist(id);
    }

    @PostMapping("/")
    public ResponseEntity<ArtistDTO> createArtist(@RequestBody ArtistDTO artsitDTO) {

        artsitDTO = artistService.createArtist(artsitDTO);

        artsitDTO = artistService.getArtist(artsitDTO.id());

        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(artsitDTO.id()).toUri();

        return ResponseEntity.created(location).body(artsitDTO);
    }

    @PutMapping("/{id}")
    public ArtistDTO replaceArtist(@PathVariable long id, @RequestBody ArtistDTO updatedArtistDTO) throws SQLException {

        return artistService.replaceArtist(id, updatedArtistDTO);
    }

    @DeleteMapping("/{id}")
    public ArtistDTO deleteArtist(@PathVariable long id) {

        return artistService.deleteArtist(id);
    }
   
}
