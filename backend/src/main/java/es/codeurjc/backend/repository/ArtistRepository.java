package es.codeurjc.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.backend.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByArtistName(String name);
}
