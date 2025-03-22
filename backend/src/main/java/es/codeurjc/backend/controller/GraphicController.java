package es.codeurjc.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.backend.dto.concert.ConcertDTO;
import es.codeurjc.backend.dto.ticket.TicketDTO;
import es.codeurjc.backend.dto.user.UserDTO;
import es.codeurjc.backend.service.ConcertService;
import es.codeurjc.backend.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
public class GraphicController {

    @Autowired
    private ConcertService concertService;

    @Autowired
    private UserService userService;

    @GetMapping("/infoGraphic/{id}")
    public Map<String, Object> obtainData(@PathVariable Long id) {

        ConcertDTO concert = concertService.getConcert(id);
        int rank018, rank1950, rank51110, age;
        rank018 = 0;
        rank1950 = 0;
        rank51110 = 0;
        for (TicketDTO tickets : concert.tickets()) {
            UserDTO userDTO = userService.getUser(tickets.userOwnerId());
            age = userDTO.age();
            int numTickets = tickets.numTickets();
            if (age <= 18) {
                rank018 += numTickets;
            } else if (age >= 19 && age <= 50) {
                rank1950 += numTickets;
            } else if (age >= 51 && age <= 110) {
                rank51110 += numTickets;
            }
        }
        Map<String, Object> datos = Map.of(
                "labels", new String[] { "0-18", "19-50", "51-110" },
                "data", new int[] { rank018, rank1950, rank51110 },
                "backgroundColor", new String[] { "red", "blue", "green" });
        return datos;

    }

    @GetMapping("/ticketsByConcert")
    public Map<String, Object> getTicketsByConcert() {
        List<ConcertDTO> concerts = new ArrayList<>(concertService.getAllConcert());

        List<String> concertNames = new ArrayList<>();
        List<Integer> ticketCounts = new ArrayList<>();
        List<String> colors = new ArrayList<>();
        List<Long> concertIds = new ArrayList<>();

        for (ConcertDTO concert : concerts) {
            concertNames.add(concert.concertName());
            ticketCounts.add(concert.tickets().size());
            colors.add(concert.color());
            concertIds.add(concert.id());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("labels", concertNames);
        response.put("data", ticketCounts);
        response.put("backgroundColor", "colors");
        response.put("concertIds", concertIds);

        return response;
    }

}
