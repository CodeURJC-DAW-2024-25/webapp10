package es.codeurjc.backend.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.backend.service.ConcertService;
import es.codeurjc.backend.service.ArtistService;
import es.codeurjc.backend.service.TicketService;
import es.codeurjc.backend.model.Concert;
import es.codeurjc.backend.model.Artist;

import java.sql.Date;
import java.sql.Time;
import java.io.IOException;
import java.util.List;

import java.security.Principal;


@Controller
public class WebController {

	@Autowired
	private ConcertService concertService;

	@Autowired
	private ArtistService artistService;

	@Autowired
	private TicketService ticketService;

	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

		if (principal != null) {

			model.addAttribute("logged", true);
			model.addAttribute("userName", principal.getName());
			model.addAttribute("admin", request.isUserInRole("ADMIN"));

		} else {
			model.addAttribute("logged", false);
		}
	}
 

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

	@GetMapping("/newconcert")
	public String newConcert(Model model) {
		List<Artist> artists = artistService.findAll();

		model.addAttribute("artists", artists);

		return "newConcert";
	}

	@PostMapping("/newconcert")
	public String newConcertProcess(
            @RequestParam String concertName,
            @RequestParam String artistName,
            @RequestParam String artistInfo,
            @RequestParam String concertDetails,
            @RequestParam Date concertDate,
            @RequestParam Time concertTime,
            @RequestParam String location,
            @RequestParam MultipartFile imageField,
            Model model) throws IOException {

        Concert concert = new Concert();
        concert.setConcertName(concertName);
        //concert.setArtists(artistName);
        concert.setConcertDetails(concertDetails);
        concert.setConcertDate(concertDate);
        concert.setConcertTime(concertTime);
        concert.setLocation(location);


        concertService.save(concert);

        model.addAttribute("concertId", concert.getId());

        return "redirect:/concert/" + concert.getId();
	}

	@GetMapping("/newartist")
    public String newArtist(Model model) {
        return "newArtist";
    }

	@PostMapping("/newartist")
    public String newArtistProcess(
            @RequestParam String artistName,
			@RequestParam String musicalStyle,
            @RequestParam String artistInfo,
            Model model) {

        Artist artist = new Artist();
        artist.setArtistName(artistName);
		artist.setMusicalStyle(musicalStyle);
        artist.setArtistInfo(artistInfo);

        artistService.save(artist);

        return "index";
	}
}