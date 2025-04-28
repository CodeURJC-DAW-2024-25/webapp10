package es.codeurjc.backend.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.backend.dto.concert.ConcertDTO;
import es.codeurjc.backend.service.ConcertService;
import es.codeurjc.backend.service.UserService;

@RestController
@RequestMapping("/api/v1/graphics")
public class GraphicRestController {

    @Autowired
    private ConcertService concertService;

    @Autowired
    private UserService userService;

    @GetMapping("/piechart/{id}")
    public Map<String, Object> getPieChartData(@PathVariable Long id) {
        ConcertDTO concert = concertService.getConcert(id);
        Map<String, Integer> ageDistribution = userService.calculateAgeDistribution(concert.tickets());

        return Map.of(
                "labels", List.of("0-18", "19-50", "51-110"),
                "data", List.of(
                        ageDistribution.getOrDefault("0-18", 0),
                        ageDistribution.getOrDefault("19-50", 0),
                        ageDistribution.getOrDefault("51-110", 0)),
                "backgroundColor", List.of("red", "blue", "green"));
    }

    @GetMapping("/bargraph")
    public Map<String, Object> getTicketsByConcertData() {
        Collection<ConcertDTO> concerts = concertService.getAllConcert();

        List<String> concertNames = concerts.stream().map(ConcertDTO::concertName).toList();
        List<Integer> ticketsSold = concerts.stream()
                .map(c -> c.tickets().stream()
                        .mapToInt(ticket -> ticket.numTickets()) // Usa aquí el getter real si no se llama 'quantity'
                        .sum())
                .toList();
        List<String> colors = concertNames.stream().map(name -> getRandomColor()).toList();

        return Map.of(
                "labels", concertNames,
                "data", ticketsSold,
                "backgroundColor", colors);
    }

    private String getRandomColor() {
        String letters = "0123456789ABCDEF";
        StringBuilder color = new StringBuilder("#");
        for (int i = 0; i < 6; i++) {
            color.append(letters.charAt((int) (Math.random() * 16)));
        }
        return color.toString();
    }

}
