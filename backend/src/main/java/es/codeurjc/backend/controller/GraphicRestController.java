package es.codeurjc.backend.controller;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    private static final int WIDTH=800;
    private static final int HEIGHT=600;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/piechart/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> obtainDataImage(@PathVariable Long id) throws IOException {

        ConcertDTO concert = concertService.getConcert(id);
        Map<String, Integer> ageDistribution = userService.calculateAgeDistribution(concert.tickets());

        PieChart chart = new PieChartBuilder()
            .width(WIDTH)
            .height(HEIGHT)
            .title("Age Distribution - " + concert.concertName())
            .build();

        chart.addSeries("0-18", ageDistribution.get("0-18"));
        chart.addSeries("19-50", ageDistribution.get("19-50"));
        chart.addSeries("51-110", ageDistribution.get("51-110"));

        chart.getStyler().setSeriesColors(new Color[]{Color.RED, Color.BLUE, Color.GREEN});
        return buildChartResponse(chart);
    }

    @GetMapping(value = "/bargraph", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getTicketsByConcertImage() throws IOException {

        Collection<ConcertDTO> concerts = concertService.getAllConcert();

        List<String> concertNames = concerts.stream().map(ConcertDTO::concertName).toList();
        List<Integer> ticketsSold = concerts.stream().map(concert -> concert.tickets().size()).toList();

        CategoryChart chart = new CategoryChartBuilder()
                .width(WIDTH)
                .height(HEIGHT)
                .title("Tickets Sold by Concert")
                .xAxisTitle("Concerts")
                .yAxisTitle("Tickets Sold")
                .build();

        chart.addSeries("Tickets", concertNames, ticketsSold);
        return buildChartResponse(chart);
    }

    private ResponseEntity<byte[]> buildChartResponse(Chart<?, ?> chart) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            BitmapEncoder.saveBitmap(chart, outputStream, BitmapEncoder.BitmapFormat.PNG);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(outputStream.toByteArray());
        }
    }

}

