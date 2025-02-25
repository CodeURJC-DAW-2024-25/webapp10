package es.codeurjc.backend.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import static org.hibernate.engine.jdbc.BlobProxy.generateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.model.Artist;
import es.codeurjc.backend.model.Concert;
import es.codeurjc.backend.model.Ticket;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.repository.ArtistRepository;
import es.codeurjc.backend.repository.ConcertRepository;
import es.codeurjc.backend.repository.TicketRepository;
import es.codeurjc.backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Service
public class DatabaseInitializer {

	@Autowired
	private ArtistRepository artistRepository;

	@Autowired
	private ConcertRepository concertRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void init() throws IOException, URISyntaxException {
		Artist TaylorSwift = new Artist("Taylor Swift", "Pop", "One of the best Pop artist in the world");
		Artist HarryStyles = new Artist("Harry Styles", "Pop", "One of the best Pop artists in the world");
		Artist ZaynMalik = new Artist("Zayn Malik", "Pop", "One of the best Pop artists in the world");
		Artist NiallHoran = new Artist("Niall Horan", "Pop", "One of the best Pop artists in the world");
		Artist LiamPayne = new Artist("Liam Payne", "Pop", "One of the best Pop artists in the world");
		Artist LouisTomlinson = new Artist("Louis Tomlinson", "Pop", "One of the best Pop artists in the world");
		Artist ShawnMendes = new Artist("Shawn Mendes", "Pop", "One of the best Pop artist in the world");
		Artist Duki = new Artist("Duki", "Trap", "One of the best Trap artist in the world");
		Artist KanyeWest = new Artist("Kanye West", "Hip-Hop", "One of the best Hip-Hop artists in the world");

		artistRepository.save(TaylorSwift);
		artistRepository.save(HarryStyles);
		artistRepository.save(ZaynMalik);
		artistRepository.save(NiallHoran);
		artistRepository.save(LiamPayne);
		artistRepository.save(LouisTomlinson);
		artistRepository.save(ShawnMendes);
		artistRepository.save(Duki);
		artistRepository.save(KanyeWest);

		Concert concert1 = new Concert("The Eras Tour Concert", "The biggest concert of Taylor Swift, featuring her greatest hits and new releases. This concert will take you on a journey through her musical career, with stunning visuals and unforgettable performances.", 
				Date.valueOf("2025-08-07"), Time.valueOf("20:00:00"), "Madrid", 100, 50, List.of(TaylorSwift), "https://maps.app.goo.gl/YMYM7WxM2PubhQTSA");
		Concert concert2 = new Concert("One Direction Reunion Tour", "The biggest concert of One Direction, reuniting all original members for an unforgettable night. Fans will enjoy a nostalgic trip with their favorite hits and new surprises.", 
				Date.valueOf("2025-08-16"), Time.valueOf("19:00:00"), "Seville", 150, 75, List.of(HarryStyles, ZaynMalik, NiallHoran, LiamPayne, LouisTomlinson), "https://maps.app.goo.gl/YMYM7WxM2PubhQTSA");
		Concert concert3 = new Concert("Wonder Tour", "The biggest concert of Shawn Mendes, performing songs from his latest album and fan favorites. Expect a night full of energy, emotion, and incredible music.", 
				Date.valueOf("2025-12-25"), Time.valueOf("20:30:00"), "Valencia", 110, 55, List.of(ShawnMendes), "https://maps.app.goo.gl/YMYM7WxM2PubhQTSA");
		Concert concert4 = new Concert("The Ameri Concert", "The biggest concert of Duki, bringing the best of Trap music. This event promises to be a high-energy show with electrifying performances.", 
				Date.valueOf("2025-03-21"), Time.valueOf("22:00:00"), "Bilbao", 130, 65, List.of(Duki), "https://maps.app.goo.gl/YMYM7WxM2PubhQTSA");
		Concert concert5 = new Concert("The Galactic Duo", "A special concert featuring Taylor Swift and Kanye West, a unique collaboration of Pop and Hip-Hop. This once-in-a-lifetime event will showcase their greatest hits and new collaborations.", 
				Date.valueOf("2025-12-15"), Time.valueOf("21:00:00"), "Barcelona", 200, 100, List.of(TaylorSwift, KanyeWest), "https://maps.app.goo.gl/YMYM7WxM2PubhQTSA");

		concertRepository.save(concert1);
		concertRepository.save(concert2);
		concertRepository.save(concert3);
		concertRepository.save(concert4);
		concertRepository.save(concert5);
	
		userRepository.save(new User("Registered User", "user", 123456789, "user@example.com", passwordEncoder.encode("user"), 20));
		userRepository.save(new User("Admin User", "admin", 987654321, "admin@example.com", passwordEncoder.encode("admin"), 21));
	}

	public void setConcertImage(Concert concert, String classpathResource) throws IOException {
		concert.setConcertImage(true);
		Resource image = new ClassPathResource(classpathResource);
		concert.setImageFile(generateProxy(image.getInputStream(), image.contentLength()));
	}
}

