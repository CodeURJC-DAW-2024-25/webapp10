package es.codeurjc.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.backend.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    
}
