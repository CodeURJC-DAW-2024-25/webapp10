package es.codeurjc.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.backend.model.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
    
}
