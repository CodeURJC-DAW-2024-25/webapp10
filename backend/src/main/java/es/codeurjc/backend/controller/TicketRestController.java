package es.codeurjc.backend.controller;

import java.net.URI;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.backend.dto.ticket.NewTicketDTO;
import es.codeurjc.backend.dto.ticket.TicketDTO;
import es.codeurjc.backend.service.ConcertService;
import es.codeurjc.backend.service.TicketService;
import es.codeurjc.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketRestController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ConcertService concertService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public Collection<TicketDTO> getTickets() {

        return ticketService.getTickets();
    }

    @GetMapping("/{id}")
    public TicketDTO getTicket(@PathVariable long id) {

        return ticketService.getTicket(id);
    }

    @PostMapping("/")
    public ResponseEntity<TicketDTO> createTicket(@RequestBody NewTicketDTO newTicketDTO, HttpServletRequest request) throws SQLException {

        Principal principal = request.getUserPrincipal();

        if(principal != null) {
			
            int price = 0;
            if ("stadiumStand".equals(newTicketDTO.ticketType())) {
                price = concertService.getConcert(newTicketDTO.concertId()).stadiumPrice();
            } else if ("concertTrack".equals(newTicketDTO.ticketType())) {
                price = concertService.getConcert(newTicketDTO.concertId()).trackPrice();
            }

            TicketDTO ticketDTO = new TicketDTO(null, newTicketDTO.ticketType(), price, userService.getUserByUsername(principal.getName()).id(), newTicketDTO.numTickets(), newTicketDTO.concertId());

            ticketDTO = ticketService.createTicket(ticketDTO);

            ticketDTO = ticketService.getTicket(ticketDTO.id());

            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(ticketDTO.id()).toUri();

            return ResponseEntity.created(location).body(ticketDTO);
		} else {
			throw new NoSuchElementException();
		}
        
    }
}
