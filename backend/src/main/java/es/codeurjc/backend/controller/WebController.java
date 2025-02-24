package es.codeurjc.backend.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import es.codeurjc.backend.model.Concert;
import es.codeurjc.backend.service.ConcertService;
import es.codeurjc.backend.service.ArtistService;
import es.codeurjc.backend.service.TicketService;

import java.util.List;

@Controller
public class WebController {

	@Autowired
	private ConcertService concertService;

	@Autowired
	private ArtistService artistService;

	@Autowired
	private TicketService ticketService;

	@GetMapping("/")
	public String show(Model model) {

		// Inicializa los conciertos si no hay ninguno (opcional)
		// concertService.initializeConcerts();

		model.addAttribute("concerts", concertService.getConcerts(0, 10));

		return "index";
	}

	/*
	 * @GetMapping("/loadMoreConcerts")
	 * public ResponseEntity<String> loadMoreConcerts(@RequestParam int page) {
	 * // Carga más conciertos al hacer clic en "Load More"
	 * var concerts = concertService.getConcerts(page, 10);
	 * StringBuilder htmlResponse = new StringBuilder();
	 * 
	 * // Generar el HTML para los conciertos y devolverlo
	 * concerts.forEach(concert -> {
	 * htmlResponse.append("<article class=\"concert-card\">")
	 * .append("<a href=\"concertInfo.html?id=")
	 * .append(concert.getId())
	 * .append("\" class=\"link\">")
	 * .append("<img src=\"")
	 * .append(concert.getConcertDetails()) // Reemplazar con la URL o path de
	 * imagen
	 * .append("\" alt=\"")
	 * .append(concert.getConcertName())
	 * .append("\">")
	 * .append("<div class=\"concert-info\">")
	 * .append("<h5>")
	 * .append(concert.getConcertName())
	 * .append("</h5>")
	 * .append("<p>Artist: ")
	 * .append(concert.getArtistName())
	 * .append("</p></div></a></article>");
	 * });
	 * return ResponseEntity.ok(htmlResponse.toString());
	 * }
	 */

	/*
	 * @GetMapping("/concert/{id}")
	 * public String showConcert(Model model, @PathVariable long id) {
	 * Concert concert = ConcertService.getConcertById(id);
	 * if (concert.isEmpty()) {
	 * return "/";
	 * } else {
	 * model.addAttribute("concert", concert);
	 * return "concertInfo";
	 * }
	 * }
	 */

}