package es.codeurjc.backend.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.codeurjc.backend.dto.artist.ArtistDTO;
import es.codeurjc.backend.dto.artist.NewArtistRequestDTO;
import es.codeurjc.backend.dto.concert.ConcertDTO;
import es.codeurjc.backend.dto.concert.NewConcertDTO;
import es.codeurjc.backend.dto.ticket.NewTicketDTO;
import es.codeurjc.backend.dto.ticket.TicketDTO;
import es.codeurjc.backend.dto.user.UserDTO;
import es.codeurjc.backend.service.ArtistService;
import es.codeurjc.backend.service.ConcertService;
import es.codeurjc.backend.service.TicketService;
import es.codeurjc.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

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
			UserDTO userDTO = userService.getUserByUsername(principal.getName());

			model.addAttribute("logged", true);
			model.addAttribute("userName", principal.getName());
			model.addAttribute("id", userDTO.id());
			model.addAttribute("admin", request.isUserInRole("ADMIN"));
			model.addAttribute("user", userDTO);

		} else {
			model.addAttribute("logged", false);
		}
	}

	@GetMapping("/")
	public String show(Model model, HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

		Pageable pageable = Pageable.ofSize(10).withPage(0);
		Page<ConcertDTO> concerts = getConcertsForUser(principal, pageable);

		model.addAttribute("concerts", concerts);
		return "index";
	}

	private Page<ConcertDTO> getConcertsForUser(Principal principal, Pageable pageable) {
		Long uId = null;

		if (principal != null) {
			UserDTO userDTO = userService.getUserByUsername(principal.getName());

			if (!userDTO.favoriteGenre().equals("None")) {
				uId = userDTO.id();
			}
		}

		return concertService.getConcerts(uId, pageable);
	}

	@GetMapping("/moreConcerts")
	public String loadMoreConcerts(@RequestParam int page, Model model, HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

		Pageable pageable = Pageable.ofSize(10).withPage(page);

		Page<ConcertDTO> concerts = null;
		concerts= getConcertsForUser(principal, pageable);

		boolean hasMore = page < concerts.getTotalPages() - 1;

		model.addAttribute("hasMore", hasMore);
		model.addAttribute("concerts", concerts);
		return "moreConcerts";
	}

	@GetMapping("/user/{id}")
	public String showUser(Model model, @PathVariable Long id, HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

		try {

			if (principal != null) {

				UserDTO userDTOprincipal = userService.getUserByUsername(principal.getName());
				UserDTO userDTO = userService.getUser(id);
				addAttributes(model, request);

				if (userDTOprincipal.equals(userDTO)) {
					model.addAttribute("user", userDTO);
					return "userPage";

				} else {
					return "loginerror";
				}
			} else {
				return "redirect:/";
			}

		} catch (NoSuchElementException e) {
			return "redirect:/";
		}

	}

	@GetMapping("/concert/{id}")
	public String showConcert(Model model, @PathVariable long id, HttpServletRequest request) {

		addAttributes(model, request);

		try {

			ConcertDTO concertDTO = concertService.getConcert(id);
			model.addAttribute("concert", concertDTO);
			model.addAttribute("concertId", id);

		} catch (NoSuchElementException e) {
			return "error";
		}
		return "concertInfo";

	}

	@GetMapping("/concert/purchasePage/{id}")
	public String showPurchasePage(Model model, @PathVariable long id, HttpServletRequest request) {

		addAttributes(model, request);
		ConcertDTO concert = concertService.getConcert(id);

		model.addAttribute("concert", concert);
		model.addAttribute("concertId", id);
		return "purchasePage";

	}
	@PostMapping("/concert/purchasePage/{id}")
	public String purchase(HttpServletRequest request, Model model, @PathVariable long id,
			NewTicketDTO newTicketDTO,
			RedirectAttributes redirectAttributes) throws IOException, SQLException {

		Principal principal = request.getUserPrincipal();
		UserDTO userDTO = userService.getUserByUsername(principal.getName());
		ConcertDTO concertDTO = concertService.getConcert(id);

		TicketDTO ticketDTO = createOrReplaceTicket(newTicketDTO, null, concertDTO, userDTO);

		updateConcertWithNewTicket(concertDTO, ticketDTO);
		updateUserWithNewTicket(userDTO, ticketDTO);

		redirectAttributes.addFlashAttribute("successMessage", "Your purchase has been completed successfully.");
		return "redirect:/";
	}

	private void updateConcertWithNewTicket(ConcertDTO concertDTO, TicketDTO ticketDTO) throws SQLException, IOException {
		List<TicketDTO> tickets = concertDTO.tickets();
		tickets.add(ticketDTO);

		ConcertDTO updatedConcert = new ConcertDTO(
				concertDTO.id(),
				concertDTO.concertName(),
				concertDTO.concertDetails(),
				concertDTO.concertDate(),
				concertDTO.concertTime(),
				concertDTO.location(),
				concertDTO.stadiumPrice(),
				concertDTO.trackPrice(),
				concertDTO.map(),
				concertDTO.concertImage(),
				null,
				concertDTO.artists(),
				tickets
		);

		concertService.createOrReplaceConcert(concertDTO.id(), updatedConcert);
	}

	private void updateUserWithNewTicket(UserDTO userDTO, TicketDTO ticketDTO) throws SQLException, IOException {
		List<TicketDTO> ticketsUser = userDTO.tickets();
		ticketsUser.add(ticketDTO);

		Integer numTicketsBought = userDTO.numTicketsBought() + ticketDTO.numTickets();

		UserDTO updatedUserDTO = new UserDTO(
				userDTO.id(),
				userDTO.fullName(),
				userDTO.userName(),
				userDTO.phone(),
				userDTO.email(),
				userDTO.password(),
				userDTO.age(),
				numTicketsBought,
				userDTO.favoriteGenre(),
				userDTO.image(),
				ticketsUser,
				userDTO.roles()
		);

		userService.createOrReplaceUser(userDTO.id(), updatedUserDTO);
	}

	@GetMapping("/newconcert")
	public String newConcert(Model model) {
		model.addAttribute("artists", artistService.getArtists());
	
		return "newConcert";
	}
	
	@PostMapping("/newconcert")
	public String newConcertProcess(
			NewConcertDTO newConcertDTO,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes,
			Model model) throws IOException, SQLException {
	
		if (newConcertDTO.concertName() == null || newConcertDTO.concertName().trim().isEmpty()) {
			bindingResult.rejectValue("concertName", "error.concertName", "Concert name cannot be empty");
		}
	
		if (newConcertDTO.concertDetails() == null || newConcertDTO.concertDetails().trim().isEmpty()) {
			bindingResult.rejectValue("concertDetails", "error.concertDetails", "Concert details cannot be empty");
		}
	
		if (newConcertDTO.concertDate() == null || newConcertDTO.concertDate().trim().isEmpty()) {
			bindingResult.rejectValue("concertDate", "error.concertDate", "Concert date cannot be empty");
		}
	
		if (newConcertDTO.concertTime() == null || newConcertDTO.concertTime().trim().isEmpty()) {
			bindingResult.rejectValue("concertTime", "error.concertTime", "Concert time cannot be empty");
		}
	
		if (newConcertDTO.location() == null || newConcertDTO.location().trim().isEmpty()) {
			bindingResult.rejectValue("location", "error.location", "Location cannot be empty");
		}
	
		if (newConcertDTO.map() == null || newConcertDTO.location().trim().isEmpty()) {
			bindingResult.rejectValue("map", "error.map", "Map cannot be empty");
		}
	
		if (newConcertDTO.stadiumPrice() == null || newConcertDTO.stadiumPrice() <= 0) {
			bindingResult.rejectValue("stadiumPrice", "error.stadiumPrice", "Stadium price must be greater than 0");
		}
	
		if (newConcertDTO.trackPrice() == null || newConcertDTO.trackPrice() <= 0) {
			bindingResult.rejectValue("trackPrice", "error.trackPrice", "Track price must be greater than 0");
		}
	
		if (newConcertDTO.artistIds() == null || newConcertDTO.artistIds().isEmpty()) {
			bindingResult.rejectValue("artistIds", "error.artistIds", "At least one artist must be selected");
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("errorMessage", "Please check the fields and correct any errors.");
			model.addAttribute("artists", artistService.getArtists());
			return "newConcert";  
		}
	
		if (concertService.existsConcertName(newConcertDTO.concertName())) {
			redirectAttributes.addFlashAttribute("errorMessage", "A concert with the same name already exists.");
			return "redirect:/newconcert"; 
		}
	
		createOrReplaceConcert(newConcertDTO, null, null, null);
		redirectAttributes.addFlashAttribute("successMessage", "Concert creation success.");
	
		return "redirect:/";
	}
	

	private ConcertDTO createOrReplaceConcert(NewConcertDTO newConcertDTO, Long concertId, Boolean removeImage,
			List<TicketDTO> tickets)
			throws SQLException, IOException {

		boolean image = false;
		if (concertId != null) {
			ConcertDTO oldconcert = concertService.getConcert(concertId);
			image = removeImage ? false : oldconcert.concertImage();
		}

		List<ArtistDTO> selectedArtists = newConcertDTO.artistIds().stream()
				.map((Long id) -> artistService.getArtist(id))
				.collect(Collectors.toList());

		ConcertDTO concertDTO = new ConcertDTO(concertId,
				newConcertDTO.concertName(), newConcertDTO.concertDetails(), newConcertDTO.concertDate(),
				newConcertDTO.concertTime(), newConcertDTO.location(), newConcertDTO.stadiumPrice(),
				newConcertDTO.trackPrice(), newConcertDTO.map(), image, null, selectedArtists, tickets);

		ConcertDTO newconcert = concertService.createOrReplaceConcert(concertId, concertDTO);

		MultipartFile imageField = newConcertDTO.imageFile();
		if (imageField != null && !imageField.isEmpty()) {
			concertService.createConcertImage(newconcert.id(), imageField.getInputStream(), imageField.getSize());
		}

		return newconcert;
	}

	@GetMapping("/concerts/{id}/image")
	public ResponseEntity<Object> getConcertPoster(@PathVariable long id) throws SQLException {

		Resource concertPoster = concertService.getConcertImage(id);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
				.body(concertPoster);

	}

	@GetMapping("/download/tickets")
    public void downloadTickets(HttpServletResponse response, Principal principal) throws IOException {
        if (principal == null) {
            response.sendRedirect("/");
            return;
        }

        UserDTO userDTO = userService.getUserByUsername(principal.getName());
        List<TicketDTO> tickets = userDTO.tickets();

        byte[] pdfBytes = ticketService.generateTicketPdf(tickets);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=tickets.pdf");
        response.getOutputStream().write(pdfBytes);
    }
	

	@GetMapping("/newartist")
	public String newArtist(Model model) {
		return "newArtist";
	}

	@PostMapping("/newartist")
	public String newArtistProcess(Model model, @Valid NewArtistRequestDTO newArtistRequestDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws IOException, SQLException {

		if (bindingResult.hasErrors()) {
			
			model.addAttribute("errorMessage", "Please verify the fields.");
			
			model.addAttribute("errors", bindingResult.getFieldErrors().stream()
				.collect(Collectors.toMap(
					FieldError::getField,
					FieldError::getDefaultMessage
				))
			);
			
			return "newArtist";
		}

		createOrReplaceArtist(newArtistRequestDTO, null);
		
		redirectAttributes.addFlashAttribute("successMessage", "Artist created successfully.");
		return "redirect:/";
	}


	@GetMapping("/editArtist/{id}")
	public String editArtistForm(Model model, @PathVariable Long id) {
		try {
			ArtistDTO artist = artistService.getArtist(id);
			model.addAttribute("artist", artist);
			return "editArtist";
		} catch (NoSuchElementException e) {
			return "error";
		}
	}

	@PostMapping("/editArtist/{id}")
	public String editArtistProcess(Model model, @Valid NewArtistRequestDTO newArtistRequestDTO, BindingResult bindingResult, @PathVariable Long id, RedirectAttributes redirectAttributes)
			throws IOException, SQLException {

		if (bindingResult.hasErrors()) {
			model.addAttribute("errorMessage", "Please verify the fields.");
			model.addAttribute("artist", artistService.getArtist(id)); 
			return "editArtist";
		}

		createOrReplaceArtist(newArtistRequestDTO, id);
	
		redirectAttributes.addFlashAttribute("successMessage", "Artist edited successfully.");
		return "redirect:/"; 
	}
	

	private ArtistDTO createOrReplaceArtist(NewArtistRequestDTO newArtistRequestDTO, Long artistId)
			throws SQLException, IOException {

		ArtistDTO artistDTO = new ArtistDTO(artistId, newArtistRequestDTO.artistName(),
				newArtistRequestDTO.musicalStyle(), newArtistRequestDTO.artistInfo());

		ArtistDTO newArtistDTO = artistService.createOrReplaceArtist(artistId, artistDTO);
		return newArtistDTO;
	}

	@GetMapping("/deleteArtist/{id}")
	public String deleteArtist(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes)
			throws SQLException {

		try {

			ArtistDTO artistDTO = artistService.getArtist(id);
			Collection<ConcertDTO> concerts = concertService.getAllConcert();

			for (ConcertDTO concert : concerts) {
				if (concert.artists().contains(artistDTO) && concert.artists().size() <= 1) {
					redirectAttributes.addFlashAttribute("errorMessage",
							"Cannot delete artist. Each concert must have at least one artist.");
					return "redirect:/";
				}
			}

			for (ConcertDTO concert : concerts) {
				if (concert.artists().contains(artistDTO)) {
					List<ArtistDTO> artists = concert.artists();
					artists.remove(artistDTO);
					ConcertDTO updatedConcert = new ConcertDTO(concert.id(),
							concert.concertName(), concert.concertDetails(), concert.concertDate(),
							concert.concertTime(), concert.location(), concert.stadiumPrice(),
							concert.trackPrice(), concert.map(), concert.concertImage(), concert.color(), artists,
							concert.tickets());
					concertService.createOrReplaceConcert(concert.id(), updatedConcert);
				}
			}

			artistService.deleteArtist(id);
			redirectAttributes.addFlashAttribute("successMessage", "Artist deleted successfully.");
			return "redirect:/";

		} catch (NoSuchElementException e) {
			return "index";
		}
	}

	@GetMapping("/editconcert/{id}")
	public String editConcertPage(Model model, @PathVariable long id, HttpServletRequest request) {

		addAttributes(model, request);
		try {
			ConcertDTO concertDTO = concertService.getConcert(id);
			model.addAttribute("artists", artistService.getArtists());
			model.addAttribute("concert", concertDTO);
			return "editConcert";
		} catch (NoSuchElementException e) {
			return "redirect:/";
		}
	}

	@PostMapping("/editconcert/{id}")
	public String editConcert(HttpServletRequest request, boolean removeImage, Model model, @PathVariable long id,
			NewConcertDTO newConcertDTO,
			RedirectAttributes redirectAttributes) throws IOException, SQLException {

		ConcertDTO concertDTO = concertService.getConcert(id);

		if (removeImage) {
			concertService.deleteConcertImage(id);
		}
		
		createOrReplaceConcert(newConcertDTO, id, removeImage, concertDTO.tickets());

		redirectAttributes.addFlashAttribute("successMessage", "Concert creation success.");

		return "redirect:/";

	}

	@GetMapping("/concert/delete/{id}")
	public String deleteConcert(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes) {

		try {
			concertService.deleteConcert(id);
			redirectAttributes.addFlashAttribute("successMessage", "Concert deleted successfully.");
			return "redirect:/";

		} catch (NoSuchElementException e) {
			return "index";
		}

	}

	private TicketDTO createOrReplaceTicket(NewTicketDTO newTicketDTO, Long ticketId, ConcertDTO concertDTO,
			UserDTO userDTO)
			throws SQLException, IOException {

		Integer price = ticketService.calculateTicketPrice(newTicketDTO.ticketType(), concertDTO);

		TicketDTO ticketDTO = new TicketDTO(ticketId,
				newTicketDTO.ticketType(), price*newTicketDTO.numTickets(), userDTO.id(), newTicketDTO.numTickets(), concertDTO.id());

		TicketDTO ticketDTOupdated = ticketService.createOrReplaceTicket(ticketId, ticketDTO);

		return ticketDTOupdated;
	}

}