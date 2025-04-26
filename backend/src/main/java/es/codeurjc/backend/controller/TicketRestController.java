package es.codeurjc.backend.controller;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import es.codeurjc.backend.dto.concert.ConcertDTO;
import es.codeurjc.backend.dto.ticket.NewTicketDTO;
import es.codeurjc.backend.dto.ticket.TicketDTO;
import es.codeurjc.backend.dto.user.UserDTO;
import es.codeurjc.backend.service.ConcertService;
import es.codeurjc.backend.service.TicketService;
import es.codeurjc.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

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
			
            ConcertDTO concertDTO = concertService.getConcert(newTicketDTO.concertId());
            Integer price = ticketService.calculateTicketPrice(newTicketDTO.ticketType(), concertDTO);

            TicketDTO ticketDTO = new TicketDTO(null, newTicketDTO.ticketType(), price*newTicketDTO.numTickets(), userService.getUserByUsername(principal.getName()).id(), newTicketDTO.numTickets(), newTicketDTO.concertId());

            ticketDTO = ticketService.createTicket(ticketDTO);

            ticketDTO = ticketService.getTicket(ticketDTO.id());

            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(ticketDTO.id()).toUri();

            return ResponseEntity.created(location).body(ticketDTO);
		} else {
			throw new NoSuchElementException();
		}
        
    }

    @GetMapping("/download/tickets")
    public ResponseEntity<byte[]> downloadTickets(Principal principal) throws IOException {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        UserDTO userDTO = userService.getUserByUsername(principal.getName());
        List<TicketDTO> tickets = userDTO.tickets();

        byte[] pdfBytes = ticketService.generateTicketPdf(tickets);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tickets.pdf")
                .body(pdfBytes);
    }
}
