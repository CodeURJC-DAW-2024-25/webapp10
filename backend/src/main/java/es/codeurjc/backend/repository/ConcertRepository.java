package es.codeurjc.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.backend.model.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    Page<Concert> findAll(Pageable pageable);

    Optional<Concert> findById(Long id);
 
}
