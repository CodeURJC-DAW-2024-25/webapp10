package es.codeurjc.backend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.codeurjc.backend.model.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    @Query(value = "SELECT c.* FROM concerts c LEFT JOIN concerts_artists ca ON c.id = ca.concerts_id LEFT JOIN artist a ON ca.artists_id = a.id JOIN user_table u ON u.id = :userId GROUP BY c.id ORDER BY SUM(IF(a.musical_style = u.favorite_genre, 1, 0)) DESC", countQuery = "SELECT count(c.id) FROM concerts c LEFT JOIN concerts_artists ca ON c.id = ca.concerts_id LEFT JOIN artist a ON ca.artists_id = a.id JOIN user_table u ON u.id = :userId GROUP BY c.id", nativeQuery = true)
    Page<Concert> findConcertsByUserPreference(@Param("userId") Long userId, Pageable pageable);

    Page<Concert> findAll(Pageable pageable);

    Optional<Concert> findById(Long id);

    Optional<Concert> findByConcertName(String concertName);
}