package es.codeurjc.backend.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.backend.model.Artist;
import es.codeurjc.backend.model.Concert;
import es.codeurjc.backend.model.Ticket;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.ArtistService;
import es.codeurjc.backend.service.ConcertService;
import es.codeurjc.backend.service.TicketService;
import es.codeurjc.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
@Controller
public class WebController {

	@Autowired
	private ConcertService concertService;

	@Autowired
	private ArtistService artistService;

	@Autowired
	private TicketService ticketService;

	@Autowired
	private UserService userService;

	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			Optional<User> user = userService.findByUserName(principal.getName());
			if (user.isPresent()) {
				model.addAttribute("logged", true);
				model.addAttribute("userName", principal.getName());
				model.addAttribute("id", user.get().getId());
				model.addAttribute("admin", request.isUserInRole("ADMIN"));
			}

		} else {
			model.addAttribute("logged", false);
		}
	}

	@GetMapping("/")
	public String show(Model model) {
		// Load the first 4 concerts.
		List<Concert> concerts = concertService.getConcerts(0, 4);
		model.addAttribute("concerts", concerts);

		return "index";
	}

	@GetMapping("/moreConcerts")
	public String loadMoreConcerts(@RequestParam int page, Model model) {
		Page<Concert> concerts = concertService.getConcertsPaginated(page);
		boolean hasMore = page < concerts.getTotalPages() - 1;
		model.addAttribute("hasMore", hasMore);

		model.addAttribute("concerts", concerts);
		return "moreConcerts";
	}

	@GetMapping("/user/{id}")
	public String showUser(Model model, @PathVariable Long id, HttpServletRequest request) {

		addAttributes(model, request);
		Optional<User> user = userService.findById(id);
		if (user.isPresent()) {
			model.addAttribute("user", user.get());
			return "userPage";
		} else {
			return "index";
		}

	}

	@GetMapping("/concert/{id}")
	public String showConcert(Model model, @PathVariable long id, HttpServletRequest request) {

		addAttributes(model, request);
		Optional<Concert> concert = concertService.findById(id);
		if (concert.isPresent()) {
			model.addAttribute("concert", concert.get());
			model.addAttribute("concertId", id);
			return "concertInfo";
		} else {
			return "index";
		}
	}

	@GetMapping("/concert/purchasePage/{id}")
	public String showPurchasePage(Model model, @PathVariable long id, HttpServletRequest request) {

		addAttributes(model, request);
		Optional<Concert> concert = concertService.findById(id);
		if (concert.isPresent()) {
			model.addAttribute("concert", concert.get());
			model.addAttribute("concertId", id);
			return "purchasePage";
		} else {
			return "index";
		}
	}

	@PostMapping("/concert/purchasePage/{id}")
	public String purchase(HttpServletRequest request, Model model, @PathVariable long id,
			@RequestParam String ticketType,
			@RequestParam Integer numTickets) throws IOException {

		Principal principal = request.getUserPrincipal();
		Integer prices;
		Optional<Concert> concerts = concertService.findById(id);
		if (!concerts.isPresent() || principal == null) {
			return "redirect:/";
		}
		Optional<User> user = userService.findByUserName(principal.getName());
		Concert concert = concerts.get();

		Ticket ticket = new Ticket();

		if ("stadiumStand".equals(ticketType)) {
			prices = concert.getStadiumPrice();
			ticket.setPrices(prices * numTickets);
		} else if ("concertTrack".equals(ticketType)) {
			prices = concert.getTrackPrice();
			ticket.setPrices(prices * numTickets);
		}

		ticket.setConcert(concert);
		concert.addTickets(ticket);
		ticket.setTicketType(ticketType);
		ticket.setUserOwner(user.get());
		ticket.setNumTickets(numTickets);
		user.get().addTickets(ticket);
		user.get().setNumTicketsBought(numTickets);
		user.get().addFavoriteGenre();
		concertService.save(concert);
		userService.save(user.get());
		ticketService.save(ticket);
		model.addAttribute("ticket", ticket.getId());
		System.out.println("Tickets del usuario: " + user.get().getTickets().size());
		return "redirect:/";
	}

	@GetMapping("/newconcert")
	public String newConcert(Model model) {
		List<Artist> artists = artistService.findAll();

		model.addAttribute("artists", artists);

		return "newConcert";
	}

	@PostMapping("/newconcert")
	public String newConcertProcess(
			@RequestParam String concertName,
			@RequestParam("artistIds") List<Long> artistIds,
			@RequestParam String concertDetails,
			@RequestParam String concertDate,
			@RequestParam String concertTime,
			@RequestParam String location,
			@RequestParam Integer stadiumPrice,
			@RequestParam Integer trackPrice,
			@RequestParam MultipartFile imageField,
			Model model) throws IOException {

		Concert concert = new Concert();
		concert.setConcertName(concertName);
		concert.setConcertDetails(concertDetails);
		concert.setConcertDate(concertDate);
		concert.setConcertTime(concertTime);
		concert.setLocation(location);
		concert.setStadiumPrice(stadiumPrice);
		concert.setTrackPrice(trackPrice);
		concert.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
		concert.setConcertImage(true);

		List<Artist> selectedArtists = artistIds.stream()
				.map(id -> artistService.findById(id)
						.orElseThrow(() -> new RuntimeException("No existe artista con ID " + id)))
				.collect(Collectors.toList());
		concert.setArtists(selectedArtists);
		concert.setMap(location);

		concertService.save(concert);

		model.addAttribute("concertId", concert.getId());

		return "redirect:/";
	}

	@GetMapping("/newartist")
	public String newArtist(Model model) {
		return "newArtist";
	}

	
	@GetMapping("/concerts/{id}/image")
	public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException {
		Optional<Concert> concert = concertService.findById(id);
	
		if (concert.isPresent() && concert.get().getImageFile() != null) {
			Resource file = new InputStreamResource(concert.get().getImageFile().getBinaryStream());
	
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
					.contentLength(concert.get().getImageFile().length())
					.body(file);
		} else {
			return ResponseEntity.notFound().build();
		}
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

		return "redirect:/";
	}
}