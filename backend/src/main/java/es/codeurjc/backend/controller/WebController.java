package es.codeurjc.backend.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.codeurjc.backend.service.ConcertService;
import es.codeurjc.backend.service.ArtistService;
import es.codeurjc.backend.service.TicketService;

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

		model.addAttribute("concerts", concertService.findAll());

		return "index";
	}

	/* @GetMapping("/concert/{id}")
	public String showConcert(Model model, @PathVariable long id) {
		Concert concert = ConcertService.getConcertById(id);
		if (concert.isEmpty()) {
			return "/";
		} else {
			model.addAttribute("concert", concert);
			return "concertInfo";
		}
	} */

}