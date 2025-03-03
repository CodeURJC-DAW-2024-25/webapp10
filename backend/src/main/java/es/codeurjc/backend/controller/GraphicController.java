package es.codeurjc.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.backend.model.Concert;
import es.codeurjc.backend.model.Ticket;
import es.codeurjc.backend.service.ConcertService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

@RestController
public class GraphicController {

    @Autowired
    private ConcertService concertService;

    @GetMapping("/infoGraphic/{id}")
    public Map<String, Object> obtainData(@PathVariable Long id) {

        Optional<Concert> concert = concertService.findById(id);
        int rank018, rank1950, rank51110, age;
        if (concert.isPresent()) {
            Concert concertGet = concert.get();
            rank018 = 0;
            rank1950 = 0;
            rank51110 = 0;
            for (Ticket tickets : concertGet.getTickets()) {
                age = tickets.getUserOwner().getAge();
                int numTickets = tickets.getNumTickets();
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
        } else {
            return null;
        }
    }

    @GetMapping("/ticketsByConcert")
    public Map<String, Object> getTicketsByConcert() {
        List<Concert> concerts = concertService.findAll();

        List<String> concertNames = new ArrayList<>();
        List<Integer> ticketCounts = new ArrayList<>();

        for (Concert concert : concerts) {
            concertNames.add(concert.getConcertName());
            ticketCounts.add(concert.countTicketsSold());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("labels", concertNames);
        response.put("data", ticketCounts);
        response.put("backgroundColor", "blue");

        return response;
    }

}
