package es.codeurjc.backend.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.codeurjc.backend.dto.ConcertDTO;
import es.codeurjc.backend.dto.NewArtistRequestDTO;
import es.codeurjc.backend.dto.NewUserDTO;
import es.codeurjc.backend.dto.TicketDTO;
import es.codeurjc.backend.dto.NewTicketDTO;
import es.codeurjc.backend.dto.UserDTO;
import es.codeurjc.backend.dto.ArtistDTO;
import es.codeurjc.backend.model.Artist;
import es.codeurjc.backend.model.Concert;
import es.codeurjc.backend.model.Ticket;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.security.CSRFHandlerConfiguration;
import es.codeurjc.backend.service.ArtistService;
import es.codeurjc.backend.service.ConcertService;
import es.codeurjc.backend.service.TicketService;
import es.codeurjc.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class WebController {

    private final CSRFHandlerConfiguration CSRFHandlerConfiguration;

	@Autowired
	private ConcertService concertService;

	@Autowired
	private ArtistService artistService;

	@Autowired
	private TicketService ticketService;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			Optional<UserDTO> user = userService.findByUserName(principal.getName());
			if (user.isPresent()) {
				model.addAttribute("logged", true);
				model.addAttribute("userName", principal.getName());
				model.addAttribute("id", user.get().getId());
				model.addAttribute("admin", request.isUserInRole("ADMIN"));
				model.addAttribute("user", user.get());
			}

		} else {
			model.addAttribute("logged", false);
		}
	}

	//------------------------------SHOWS THE INDEX PAGE----------------------------------------------
	@GetMapping("/")
	public String show(Model model, HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

		Pageable pageable = Pageable.ofSize(10).withPage(0);

		Long uId = null;

		if (principal != null) {
			Optional<UserDTO> user = userService.findByUserName(principal.getName());

			user.get().addFavoriteGenre();

			if (user.isPresent() && !user.get().getFavoriteGenre().equals("None")) {
				uId = user.get().getId();
			}
		}

		Page<ConcertDTO> concerts = concertService.getConcerts(uId, pageable);

		model.addAttribute("concerts", concerts);
		return "index";
	}

	//------------------------------PAGE TO SHOW MORE CONCERTS------------------------------
	@GetMapping("/moreConcerts")
	public String loadMoreConcerts(@RequestParam int page, Model model, HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

		Pageable pageable = Pageable.ofSize(10).withPage(page);

		Page<ConcertDTO> concerts = null;

		Long uId = null;

		if (principal != null) {
			Optional<UserDTO> user = userService.findByUserName(principal.getName());

			if (user.isPresent() && !user.get().getFavoriteGenre().equals("None")) {
				uId = user.get().getId();
			}
		}

		concerts = concertService.getConcerts(uId, pageable);

		boolean hasMore = page < concerts.getTotalPages() - 1;

		model.addAttribute("hasMore", hasMore);
		model.addAttribute("concerts", concerts);
		return "moreConcerts";
	}

	//---------------------------------------------------USER---------------------------------------------------
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

	@PostMapping("/edituser/{id}")
	public String editUser(HttpServletRequest request, boolean removeImage, Model model, @PathVariable long id,NewUserDTO newUserDTO) throws IOException, SQLException {

		UserDTO userDTO= userService.replaceUser(id,newUserDTO,removeImage);

		return "redirect:/user/"+userDTO.id();

	}

	private void updateImage(Concert concert, boolean removeImage, MultipartFile imageField)
			throws IOException, SQLException {

		if (removeImage) {
			concert.setImageFile(null);
			concert.setConcertImage(false);
		} else if (imageField != null && !imageField.isEmpty()) {
			concert.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
			concert.setConcertImage(true);
		} else {
			ConcertDTO dbConcert = concertService.findById(concert.getId()).orElseThrow();
			if (dbConcert.getConcertImage()) {
				concert.setImageFile(BlobProxy.generateProxy(dbConcert.getImageFile().getBinaryStream(),
						dbConcert.getImageFile().length()));
				concert.setConcertImage(true);
			}
		}
	}

	@GetMapping("/download/tickets")
	public void downloadTickets(HttpServletResponse response, Principal principal) throws IOException {
		if (principal == null) {
			response.sendRedirect("/");
			return;
		}

		UserDTO userDTO = userService.getUserByUsername(principal.getName());

		List<TicketDTO> tickets = userDTO.tickets();

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=tickets.pdf");

		try (PDDocument document = new PDDocument()) {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);

			try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

				PDColor titleColor = new PDColor(new float[] { 75 / 255f, 0 / 255f, 130 / 255f }, PDDeviceRGB.INSTANCE);
				contentStream.setNonStrokingColor(titleColor);

				contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
				contentStream.beginText();
				contentStream.newLineAtOffset(100, 750);
				contentStream.showText("Ticket Purchase History - TicketZone Fest");
				contentStream.endText();

				int yPosition = 700;
				for (TicketDTO ticket : tickets) {
					ConcertDTO concertDTO= concertService.getConcert(ticket.concertId());

					PDColor concertNameColor = new PDColor(new float[] { 84 / 255f, 26 / 255f, 113 / 255f },
							PDDeviceRGB.INSTANCE);
					contentStream.setNonStrokingColor(concertNameColor);

					contentStream.beginText();
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
					contentStream.newLineAtOffset(50, yPosition);
					contentStream.showText("Concert: " + concertDTO.concertName());
					contentStream.endText();

					contentStream.setNonStrokingColor(0, 0, 0);
					contentStream.setFont(PDType1Font.HELVETICA, 12);

					contentStream.beginText();
					contentStream.setFont(PDType1Font.HELVETICA, 12);
					contentStream.newLineAtOffset(50, yPosition - 20);
					contentStream.showText("Date: " + concertDTO.concertDate());
					contentStream.newLineAtOffset(0, -15);
					contentStream.showText("Location: " + concertDTO.location());
					contentStream.newLineAtOffset(0, -15);
					contentStream.showText("Number of Tickets: " + ticket.numTickets());
					contentStream.newLineAtOffset(0, -15);
					contentStream.showText("Total Price: " + ticket.prices() + "€");
					contentStream.endText();

					yPosition -= 100;
					if (yPosition < 100) {
						break;
					}
				}
			}
			document.save(response.getOutputStream());
		}
	}

	//---------------------------------------------------CONCERTS---------------------------------------------------
	@GetMapping("/concert/{id}")
	public String showConcert(Model model, @PathVariable long id, HttpServletRequest request) {

		addAttributes(model, request);
		Optional<ConcertDTO> concertDTO = concertService.getConcert(id);
		if (concertDTO.isPresent()) {
			model.addAttribute("concert", concertDTO.get());
			model.addAttribute("concertId", id);
			return "concertInfo";
		} else {
			return "concertError";
		}
	}

	@GetMapping("/concert/purchasePage/{id}")
	public String showPurchasePage(Model model, @PathVariable long id, HttpServletRequest request) {

		addAttributes(model, request);
		Optional<ConcertDTO> concert = concertService.findById(id);
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
			NewTicketDTO newTicketDTO ,
			RedirectAttributes redirectAttributes) throws IOException, SQLException {

		ConcertDTO concertDTO = concertService.getConcert(id);
		Principal principal = request.getUserPrincipal();
		UserDTO userDTO = userService.getUserByUsername(principal.getName());

		TicketDTO ticketDTO = createOrReplaceBook(newTicketDTO,null, concertDTO, userDTO);

		//concert.addTickets(ticket);
		/* user.get().addTickets(ticket);
		user.get().setNumTicketsBought(numTickets);
		user.get().addFavoriteGenre(); */

		redirectAttributes.addFlashAttribute("successMessage", "Your purchase has been completed successfully.");

		return "redirect:/";
	}

	@GetMapping("/newconcert")
	public String newConcert(Model model) {
		List<ArtistDTO> artists = artistService.findAll();

		model.addAttribute("artists", artists);

		return "newConcert";
	}

	@PostMapping("/newconcert")
	public String newConcertProcess(
			@RequestParam String concertName,
			@RequestParam(value = "artistIds", required = false) List<Long> artistIds,
			@RequestParam String concertDetails,
			@RequestParam String concertDate,
			@RequestParam String concertTime,
			@RequestParam String location,
			@RequestParam String map,
			@RequestParam Integer stadiumPrice,
			@RequestParam Integer trackPrice,
			@RequestParam MultipartFile imageFile,
			Model model) throws IOException {

		List<ArtistDTO> artists = artistService.findAll();
		model.addAttribute("artists", artists);

		if (concertName == null || concertName.isEmpty() || concertName.length() < 2) {
			model.addAttribute("newConcertError", "Concert name is required and must be at least 2 characters long.");
			return "newConcert";
		}

		if ((artistIds == null || artistIds.isEmpty())) {
			model.addAttribute("newConcertError", "At least one artist is required.");
			return "newConcert";
		}

		if (concertDetails == null || concertDetails.isEmpty() || concertDetails.length() < 8) {
			model.addAttribute("newConcertError",
					"Concert details are required and must be at least 8 characters long.");
			return "newConcert";
		}

		if (concertDate == null || concertDate.isEmpty()) {
			model.addAttribute("newConcertError", "Concert date is required.");
			return "newConcert";
		}

		if (concertTime == null || concertTime.isEmpty()) {
			model.addAttribute("newConcertError", "Concert time is required.");
			return "newConcert";
		}

		if (location == null || location.isEmpty()) {
			model.addAttribute("newConcertError", "Location is required.");
			return "newConcert";
		}

		if (map == null || map.isEmpty()) {
			model.addAttribute("newConcertError", "Map is required.");
			return "newConcert";
		}

		if (!map.startsWith("<iframe src=\"https://www.google.com/maps/embed?")) {
			model.addAttribute("newConcertError", "You must provide a valid embedded Google Maps iframe.");
			return "newconcert";
		}

		if (stadiumPrice == null || stadiumPrice <= 0) {
			model.addAttribute("newConcertError", "Stadium price is required and must be greater than 0.");
			return "newConcert";
		}

		if (trackPrice == null || trackPrice <= 0) {
			model.addAttribute("newConcertError", "Track price is required and must be greater than 0.");
			return "newConcert";
		}

		List<ArtistDTO> selectedArtists = artistIds.stream()
				.map(id -> artistService.findById(id)
						.orElseThrow(() -> new RuntimeException("No existe artista con ID " + id)))
				.collect(Collectors.toList());

		ConcertDTO concert = new ConcertDTO(concertName, concertDetails, concertDate, concertTime, location,
				stadiumPrice,
				trackPrice, selectedArtists, map);

		if (imageFile != null && !imageFile.isEmpty()) {
			concert.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
			concert.setConcertImage(true);
		}

		concertService.save(concert);

		model.addAttribute("concertId", concert.getId());

		return "redirect:/";
	}

	@GetMapping("/concerts/{id}/image")
	public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException {
		Optional<ConcertDTO> concert = concertService.findById(id);

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

	@GetMapping("/concert/delete/{id}")
	public String deleteConcert(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		Optional<ConcertDTO> concertOptional = concertService.findById(id);

		if (concertOptional.isPresent()) {
			concertService.deleteById(id);
			redirectAttributes.addFlashAttribute("successMessage", "Concert deleted successfully.");
			return "redirect:/";
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Concert not found.");
			return "concertError";
		}

	}

	//---------------------------------------------------TICKET---------------------------------------------------
	private TicketDTO createOrReplaceBook(NewTicketDTO newTicketDTO, Long ticketId, ConcertDTO concertDTO, UserDTO userDTO)
	throws SQLException, IOException {

		Integer prices=0;
		if ("stadiumStand".equals(newTicketDTO.ticketType())) {
			prices = concertDTO.stadiumPrice();
		} else if ("concertTrack".equals(newTicketDTO.ticketType())) {
			prices = concertDTO.trackPrice();
		}

	TicketDTO ticketDTO = new TicketDTO(ticketId,
		newTicketDTO.ticketType(), prices, userDTO.id(), newTicketDTO.numTickets(), concertDTO.id());
		
	ticketService.createOrReplaceTicket(ticketId, ticketDTO);

	return ticketDTO;
	}

	//---------------------------------------------------ARTIST---------------------------------------------------
	@GetMapping("/newartist")
	public String newArtist(Model model) {
		return "newArtist";
	}

	@PostMapping("/newartist")
	public String newArtistProcess(Model model, NewArtistRequestDTO newArtistRequestDTO) throws IOException, SQLException {

		ArtistDTO newArtistDTO = createOrReplaceArtist(newArtistRequestDTO, null);
		return "redirect:/";
	}

	
	@GetMapping("/editArtist/{id}")
	public String editArtistForm(Model model, @PathVariable Long id) {
		try {
			ArtistDTO artist = artistService.getArtist(id);
			model.addAttribute("artist", artist);
			return "editArtist";
		} catch (NoSuchElementException e) {
			return "redirect:/";
		}
	}

	@PostMapping("/editArtist/{id}")
	public String editArtistProcess(Model model, NewArtistRequestDTO newArtistRequestDTO, long artistId) throws IOException, SQLException {

		ArtistDTO artistDTO = createOrReplaceArtist(newArtistRequestDTO, artistId);
		
		return "redirect:/";
	}

	private ArtistDTO createOrReplaceArtist(NewArtistRequestDTO newArtistRequestDTO, Long artistId) throws SQLException, IOException {

		ArtistDTO artistDTO = new ArtistDTO(artistId, newArtistRequestDTO.artistName(), newArtistRequestDTO.musicalStyle(), newArtistRequestDTO.artistInfo());
		
		ArtistDTO newArtistDTO = artistService.createOrReplaceArtist(artistId, artistDTO);
		return newArtistDTO;
	}

	@GetMapping("/deleteArtist/{id}")
	public String deleteArtist(Model model, @PathVariable Long id) {

		try {
			ArtistDTO artistDTO = artistService.deleteArtist(id);
			model.addAttribute("artist", artistDTO);

			return "redirect:/";

		} catch (NoSuchElementException e) {
			return "artistNotFound";
		}
	}

}