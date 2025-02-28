package es.codeurjc.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.codeurjc.backend.model.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    Page<Concert> findAll(Pageable pageable);

}
