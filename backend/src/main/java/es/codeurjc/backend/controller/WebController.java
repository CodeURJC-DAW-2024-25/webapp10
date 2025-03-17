package es.codeurjc.backend.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
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
import es.codeurjc.backend.dto.TicketDTO;
import es.codeurjc.backend.dto.ArtistDTO;
import es.codeurjc.backend.dto.UserDTO;
import es.codeurjc.backend.model.Artist;
import es.codeurjc.backend.model.Concert;
import es.codeurjc.backend.model.Ticket;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.ArtistService;
import es.codeurjc.backend.service.ConcertService;
import es.codeurjc.backend.service.TicketService;
import es.codeurjc.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

	@GetMapping("/user/{id}")
	public String showUser(Model model, @PathVariable Long id, HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			Optional<UserDTO> user = userService.findByUserName(principal.getName());
			addAttributes(model, request);
			Optional<UserDTO> user2 = userService.findById(id);
			if (user.equals(user2)) {
				if (user2.isPresent()) {

					user2.get().addFavoriteGenre();

					model.addAttribute("user", user2.get());
					return "userPage";
				} else {
					return "index";
				}
			} else {
				return "loginerror";
			}
		} else {
			return "redirect:/";
		}

	}

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
			@RequestParam String ticketType,
			@RequestParam Integer numTickets,
			RedirectAttributes redirectAttributes) throws IOException {

		Principal principal = request.getUserPrincipal();
		Integer prices;
		Optional<ConcertDTO> concerts = concertService.findById(id);
		if (!concerts.isPresent() || principal == null) {
			return "redirect:/";
		}
		Optional<UserDTO> user = userService.findByUserName(principal.getName());
		ConcertDTO concert = concerts.get();

		TicketDTO ticket = new TicketDTO();

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

		ConcertDTO concert = new ConcertDTO(concertName, concertDetails, concertDate, concertTime, location, stadiumPrice,
				trackPrice, selectedArtists, map);

		if (imageFile != null && !imageFile.isEmpty()) {
			concert.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
			concert.setConcertImage(true);
		}

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

	@GetMapping("/download/tickets")
	public void downloadTickets(HttpServletResponse response, Principal principal) throws IOException {
		if (principal == null) {
			response.sendRedirect("/");
			return;
		}

		Optional<UserDTO> userOptional = userService.findByUserName(principal.getName());
		if (!userOptional.isPresent()) {
			response.sendRedirect("/");
			return;
		}

		UserDTO user = userOptional.get();
		List<TicketDTO> tickets = user.getTickets();

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
				for (Ticket ticket : tickets) {

					PDColor concertNameColor = new PDColor(new float[] { 84 / 255f, 26 / 255f, 113 / 255f },
							PDDeviceRGB.INSTANCE);
					contentStream.setNonStrokingColor(concertNameColor);

					contentStream.beginText();
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
					contentStream.newLineAtOffset(50, yPosition);
					contentStream.showText("Concert: " + ticket.getConcert().getConcertName());
					contentStream.endText();

					contentStream.setNonStrokingColor(0, 0, 0);
					contentStream.setFont(PDType1Font.HELVETICA, 12);

					contentStream.beginText();
					contentStream.setFont(PDType1Font.HELVETICA, 12);
					contentStream.newLineAtOffset(50, yPosition - 20);
					contentStream.showText("Date: " + ticket.getConcert().getConcertDate());
					contentStream.newLineAtOffset(0, -15);
					contentStream.showText("Location: " + ticket.getConcert().getLocation());
					contentStream.newLineAtOffset(0, -15);
					contentStream.showText("Number of Tickets: " + ticket.getNumTickets());
					contentStream.newLineAtOffset(0, -15);
					contentStream.showText("Total Price: " + ticket.getPrices() + "€");
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

	@PostMapping("/newartist")
	public String newArtistProcess(
			@RequestParam String artistName,
			@RequestParam String musicalStyle,
			@RequestParam String artistInfo,
			Model model) {

		if (artistService.existsName(artistName)) {
			model.addAttribute("newArtistError", "Artist with name " + artistName + " already exists.");
			return "newArtist";
		}

		if (artistName == null || artistName.isEmpty()) {
			model.addAttribute("newArtistError", "Artist name is required.");
			return "newArtist";
		}
		if (musicalStyle == null || musicalStyle.isEmpty()) {
			model.addAttribute("newArtistError", "Musical style is required.");
			return "newArtist";
		}
		if (artistInfo == null || artistInfo.isEmpty()) {
			model.addAttribute("newArtistError", "Artist information is required.");
			return "newArtist";
		}

		ArtistDTO artist = new ArtistDTO();
		artist.setArtistName(artistName);
		artist.setMusicalStyle(musicalStyle);
		artist.setArtistInfo(artistInfo);

		artistService.save(artist);

		return "redirect:/";
	}

	@GetMapping("/editconcert/{id}")
	public String editConcertPage(Model model, @PathVariable long id, HttpServletRequest request) {

		addAttributes(model, request);
		Optional<ConcertDTO> concert = concertService.findById(id);
		if (concert.isPresent()) {
			List<ArtistDTO> artists = artistService.findAll();
			model.addAttribute("artists", artists);
			model.addAttribute("concert", concert.get());
			return "editConcert";
		} else {
			return "index";
		}
	}

	@PostMapping("/editconcert/{id}")
	public String editConcert(HttpServletRequest request, boolean removeImage, Model model, @PathVariable long id,
			@RequestParam String concertName,
			@RequestParam("artistIds") List<Long> artistIds,
			@RequestParam String concertDetails,
			@RequestParam String concertDate,
			@RequestParam String concertTime,
			@RequestParam String location,
			@RequestParam String map,
			@RequestParam Integer stadiumPrice,
			@RequestParam Integer trackPrice,
			@RequestParam MultipartFile imageFile,
			RedirectAttributes redirectAttributes) throws IOException, SQLException {

		if (concertName == null || concertName.isEmpty()) {
			model.addAttribute("editConcertError", "Concert name is required and must be at least 2 characters long.");
			return "editConcert";
		}
		if (concertDetails == null || concertDetails.isEmpty()) {
			model.addAttribute("editConcertError",
					"Concert details are required and must be at least 8 characters long.");
			return "editConcert";
		}

		if (artistIds == null || artistIds.isEmpty() || (artistIds.size() == 1 && artistIds.get(0) == 0)) {
			model.addAttribute("editConcertError", "At least one artist is required.");
			return "editConcert";
		}

		if (concertDate == null || concertDate.isEmpty()) {
			model.addAttribute("editConcertError", "Concert date is required.");
			return "editConcert";
		}

		if (concertTime == null || concertTime.isEmpty()) {
			model.addAttribute("editConcertError", "Concert time is required.");
			return "editConcert";
		}

		if (location == null || location.isEmpty()) {
			model.addAttribute("editConcertError", "Location is required.");
			return "editConcert";
		}

		if (map == null || map.isEmpty()) {
			model.addAttribute("editConcertError", "Map is required.");
			return "editConcert";
		}

		if (stadiumPrice == null || stadiumPrice <= 0) {
			model.addAttribute("editConcertError", "Stadium price is required and must be greater than 0.");
			return "editConcert";
		}

		if (trackPrice == null || trackPrice <= 0) {
			model.addAttribute("editConcertError", "Track price is required and must be greater than 0.");
			return "editConcert";
		}

		Optional<ConcertDTO> concertOptional = concertService.findById(id);
		if (!concertOptional.isPresent()) {
			model.addAttribute("editConcertError", "Concert not found.");
			return "editConcert";
		}

		Principal principal = request.getUserPrincipal();
		Optional<UserDTO> user = userService.findByUserName(principal.getName());

		if (!user.isPresent() || principal == null) {
			return "redirect:/";
		}

		ConcertDTO concert = concertOptional.get();
		concert.setConcertName(concertName);
		concert.setConcertDetails(concertDetails);
		concert.setConcertDate(concertDate);
		concert.setConcertTime(concertTime);
		concert.setLocation(location);
		concert.setMap(map);
		concert.setStadiumPrice(stadiumPrice);
		concert.setTrackPrice(trackPrice);
		updateImage(concert, removeImage, imageFile);

		List<ArtistDTO> selectedArtists = artistIds.stream()
				.map(idArtist -> artistService.findById(idArtist)
						.orElseThrow(() -> new RuntimeException("Artist with ID " + idArtist + " does not exist")))
				.collect(Collectors.toList());
		concert.setArtists(selectedArtists);

		concertService.save(concert);

		user.get().addFavoriteGenre();
		userService.save(user.get());

		model.addAttribute("concertId", concert.getId());

		redirectAttributes.addFlashAttribute("successMessage", "Concert edit success.");

		return "redirect:/";
	}

	@GetMapping("/edituser/{id}")
	public String editUserPage(Model model, @PathVariable long id, HttpServletRequest request) {

		addAttributes(model, request);
		Optional<UserDTO> user = userService.findById(id);
		if (user.isPresent()) {
			model.addAttribute("user", user.get());
			return "editUser";
		} else {
			return "index";
		}
	}

	@PostMapping("/edituser/{id}")
	public String editUser(HttpServletRequest request, boolean removeImage, Model model, @PathVariable long id,
			@RequestParam String fullName,
			@RequestParam Integer phone,
			@RequestParam String email,
			@RequestParam Integer age,
			@RequestParam MultipartFile profilePhoto,
			RedirectAttributes redirectAttributes) throws IOException, SQLException {

		Optional<UserDTO> userOptional = userService.findById(id);
		if (!userOptional.isPresent()) {
			model.addAttribute("edituserError", "User not found.");
			return "editUser";
		}

		if (fullName == null || fullName.isEmpty()) {
			model.addAttribute("edituserError", "Fill the gap name");
			return "editUser";
		}

		if (email == null || email.isEmpty() || !email.contains("@") || !email.contains(".com")) {
			model.addAttribute("error", "Write a valid email");
			return "register";
		}

		if (phone == null || String.valueOf(phone).length() != 9) {
			model.addAttribute("edituserError", "Phone number must be 9 digits");
			return "editUser";
		}

		if (age == null || age < 0 || age > 110) {
			model.addAttribute("edituserError", "Enter an age between 0-110");
			return "editUser";
		}

		Principal principal = request.getUserPrincipal();
		Optional<UserDTO> userPrincipal = userService.findByUserName(principal.getName());

		if (!userPrincipal.isPresent() || principal == null) {
			return "redirect:/";
		}

		UserDTO user = userOptional.get();
		user.setFullName(fullName);
		user.setAge(age);
		user.setPhone(phone);
		user.setEmail(email);
		updateImageUser(user, removeImage, profilePhoto);

		userService.save(user);

		model.addAttribute("userId", user.getId());

		redirectAttributes.addFlashAttribute("successMessage", "User edit success.");

		return "redirect:/";

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

	private void updateImageUser(User user, boolean removeImage, MultipartFile imageField)
			throws IOException, SQLException {

		if (removeImage) {
			user.setProfilePhoto(null);
			user.setImage(false);
		} else if (imageField != null && !imageField.isEmpty()) {
			user.setProfilePhoto(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
			user.setImage(true);
		} else {
			UserDTO dbUser = userService.findById(user.getId()).orElseThrow();
			if (dbUser.getImage()) {
				user.setProfilePhoto(BlobProxy.generateProxy(dbUser.getProfilePhoto().getBinaryStream(),
						dbUser.getProfilePhoto().length()));
				user.setImage(true);
			}
		}
	}

	@GetMapping("/editArtist/{id}")
	public String editArtistForm(@PathVariable Long id, Model model) {
		Optional<ArtistDTO> artist = artistService.findById(id);
		if (artist.isPresent()) {
			model.addAttribute("artist", artist.get());
			return "editArtist";
		} else {
			return "redirect:/";
		}
	}

	@PostMapping("/editArtist/{id}")
	public String editArtistProcess(@PathVariable Long id,
			@RequestParam String artistName,
			@RequestParam String musicalStyle,
			@RequestParam String artistInfo,
			Model model,
			RedirectAttributes redirectAttributes) {

		Optional<ArtistDTO> artistOptional = artistService.findById(id);

		ArtistDTO artist = artistOptional.get();
		if (artistName == null || artistName.isEmpty()) {
			model.addAttribute("editArtistError", "Artist name is required.");
			model.addAttribute("artist", artist);
			return "editArtist";
		}
		if (musicalStyle == null || musicalStyle.isEmpty()) {
			model.addAttribute("editArtistError", "Musical style is required.");
			model.addAttribute("artist", artist);
			return "editArtist";
		}
		if (artistInfo == null || artistInfo.isEmpty()) {
			model.addAttribute("editArtistError", "Artist information is required.");
			model.addAttribute("artist", artist);
			return "editArtist";
		}

		artist.setArtistName(artistName);
		artist.setMusicalStyle(musicalStyle);
		artist.setArtistInfo(artistInfo);

		artistService.save(artist);

		redirectAttributes.addFlashAttribute("successMessage", "Artist edited successfully.");

		return "redirect:/";
	}

	@GetMapping("/deleteArtist/{id}")
	public String deleteArtist(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		Optional<ArtistDTO> artistOptional = artistService.findById(id);
		if (artistOptional.isPresent()) {
			ArtistDTO artist = artistOptional.get();

			List<ConcertDTO> concerts = concertService.findAllConcerts();

			for (ConcertDTO concert : concerts) {
				if (concert.getArtists().contains(artist) && concert.getArtists().size() <= 1) {
					redirectAttributes.addFlashAttribute("errorMessage",
							"Cannot delete artist. Each concert must have at least one artist.");
					return "redirect:/";
				}
			}

			for (ConcertDTO concert : concerts) {
				if (concert.getArtists().contains(artist)) {
					concert.getArtists().remove(artist);
					concertService.save(concert);
				}
			}

			artistService.deleteById(id);
			redirectAttributes.addFlashAttribute("successMessage", "Artist successfully removed.");
			return "redirect:/";
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Artist not found.");
			return "redirect:/";
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

}