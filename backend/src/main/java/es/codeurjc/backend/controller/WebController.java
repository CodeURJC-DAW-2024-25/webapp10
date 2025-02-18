package es.codeurjc.backend.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {


    @GetMapping("/")
	public String show(Model model) {
		return "/";
	}

	@GetMapping("/concert/{id}")
	public String showConcert(Model model, @PathVariable long id) {
		Concert concert = ConcertService.getConcertById(id);
		if (concert.isEmpty()) {
			return "/";
		} else {
			model.addAttribute("concert", concert);
			return "concertInfo";
		}
	}

}