package es.codeurjc.backend.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import java.awt.Color;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.backend.dto.concert.ConcertDTO;
import es.codeurjc.backend.dto.ticket.TicketDTO;
import es.codeurjc.backend.dto.user.UserDTO;
import es.codeurjc.backend.service.ConcertService;
import es.codeurjc.backend.service.UserService;

@RestController
@RequestMapping("/api/v1/graphics")
public class GraphicRestController {

    @Autowired
    private ConcertService concertService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/piechart/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> obtainDataImage(@PathVariable Long id) throws IOException {

        ConcertDTO concert = concertService.getConcert(id);
        int rank018 = 0, rank1950 = 0, rank51110 = 0;

        for (TicketDTO ticket : concert.tickets()) {
            UserDTO user = userService.getUser(ticket.userOwnerId());
            int age = user.age();
            int numTickets = ticket.numTickets();
            
            if (age <= 18) {
                rank018 += numTickets;
            } else if (age >= 19 && age <= 50) {
                rank1950 += numTickets;
            } else {
                rank51110 += numTickets;
            }
        }

        PieChart chart = new PieChartBuilder().width(800).height(600).title("Age Distribution - " + concert.concertName()).build();

        chart.addSeries("0-18", rank018);
        chart.addSeries("19-50", rank1950);
        chart.addSeries("51-110", rank51110);

        chart.getStyler().setSeriesColors(new Color[]{Color.RED, Color.BLUE, Color.GREEN});

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BitmapEncoder.saveBitmap(chart, outputStream, BitmapEncoder.BitmapFormat.PNG);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(outputStream.toByteArray());
    }

    @GetMapping(value = "/bargraph", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getTicketsByConcertImage() throws IOException {

        Collection<ConcertDTO> concerts = concertService.getAllConcert();

        List<String> concertNames = concerts.stream().map(ConcertDTO::concertName).toList();
        List<Integer> ticketsSold = concerts.stream().map(concert -> concert.tickets().size()).toList();

        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title("Tickets Sold by Concert")
                .xAxisTitle("Concerts")
                .yAxisTitle("Tickets Sold")
                .build();

        chart.addSeries("Tickets", concertNames, ticketsSold);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BitmapEncoder.saveBitmap(chart, outputStream, BitmapEncoder.BitmapFormat.PNG);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(outputStream.toByteArray());
    }

}

