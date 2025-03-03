package es.codeurjc.backend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.codeurjc.backend.model.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    Page<Concert> findAll(Pageable pageable);

    Optional<Concert> findById(Long id);

}
