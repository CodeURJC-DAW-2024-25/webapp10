package es.codeurjc.backend.controller;

import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.codeurjc.backend.dto.TicketDTO;
import es.codeurjc.backend.service.TicketService;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/tickets")
public class TicketRestController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/")
    public Collection<TicketDTO> getTickets() {

        return ticketService.getTickets();
    }

    @GetMapping("/{id}")
    public TicketDTO getTicket(@PathVariable long id) {

        return ticketService.getTicket(id);
    }

    @PostMapping("/")
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO) {

        ticketDTO = ticketService.createTicket(ticketDTO);

        ticketDTO = ticketService.getTicket(ticketDTO.id());

        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(ticketDTO.id()).toUri();

        return ResponseEntity.created(location).body(ticketDTO);
    }

    @PutMapping("/{id}")
    public TicketDTO replaceShop(@PathVariable long id, @RequestBody TicketDTO updatedTicketDTO) throws SQLException {

        return ticketService.replaceTicket(id, updatedTicketDTO);
    }

    @DeleteMapping("/{id}")
    public TicketDTO deleteTicket(@PathVariable long id) {

        return ticketService.deleteTicket(id);
    }
   
}
