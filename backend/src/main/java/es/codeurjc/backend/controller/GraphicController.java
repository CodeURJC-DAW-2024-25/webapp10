package es.codeurjc.backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.backend.dto.concert.ConcertDTO;
import es.codeurjc.backend.service.ConcertService;
import es.codeurjc.backend.service.UserService;
import es.codeurjc.backend.dto.ticket.TicketDTO;

@RestController
public class GraphicController {

    @Autowired
    private ConcertService concertService;

    @Autowired
    private UserService userService;

    @GetMapping("/infoGraphic/{id}")
    public Map<String, Object> obtainData(@PathVariable Long id) {

        ConcertDTO concert = concertService.getConcert(id);
        Map<String, Integer> ageDistribution = userService.calculateAgeDistribution(concert.tickets());
        return Map.of(
                "labels", new String[] { "0-18", "19-50", "51-110" },
                "data",
                new int[] { ageDistribution.get("0-18"), ageDistribution.get("19-50"), ageDistribution.get("51-110") },
                "backgroundColor", new String[] { "red", "blue", "green" });
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
            int totalTickets = concert.tickets().stream().mapToInt(TicketDTO::numTickets).sum();

            ticketCounts.add(totalTickets);
            colors.add(concert.color());
            concertIds.add(concert.id());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("labels", concertNames);
        response.put("data", ticketCounts);
        response.put("backgroundColor", colors);
        response.put("concertIds", concertIds);

        return response;
    }

}
