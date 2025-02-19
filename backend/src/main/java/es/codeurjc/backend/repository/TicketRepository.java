package es.codeurjc.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.backend.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
}