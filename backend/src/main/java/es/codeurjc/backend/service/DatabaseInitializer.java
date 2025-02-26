package es.codeurjc.backend.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.List;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.model.Artist;
import es.codeurjc.backend.model.Concert;
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
		Artist RauwAlejandro = new Artist("Rauw Alejandro", "Latin", "One of the best Latin artists in the world");
		Artist ArianaGrande = new Artist("Ariana Grande", "Pop", "One of the best Pop artists in the world");
		Artist Saiko = new Artist("Saiko", "Latin", "One of the best Latin artists in the world");
		Artist BadBunny = new Artist("Bad Bunny", "Latin", "One of the best Latin artists in the world");
		Artist BadGyal = new Artist("Bad Gyal", "Latin", "One of the best Latin artists in the world");
		Artist EmiliaMernes = new Artist("Emilia Mernes", "Latin", "One of the best Latin artists in the world");
		Artist Quevedo = new Artist("Quevedo", "Latin", "One of the best Latin artists in the world");
		Artist Aitana = new Artist("Aitana", "Pop", "One of the best Pop artists in the world");
		Artist KarolG = new Artist("Karol G", "Reggaeton", "One of the best Reggaeton artists in the world");

		artistRepository.save(Quevedo);
		artistRepository.save(Aitana);
		artistRepository.save(KarolG);
		artistRepository.save(TaylorSwift);
		artistRepository.save(HarryStyles);
		artistRepository.save(ZaynMalik);
		artistRepository.save(NiallHoran);
		artistRepository.save(LiamPayne);
		artistRepository.save(LouisTomlinson);
		artistRepository.save(ShawnMendes);
		artistRepository.save(Duki);
		artistRepository.save(KanyeWest);
		artistRepository.save(RauwAlejandro);
		artistRepository.save(ArianaGrande);
		artistRepository.save(Saiko);
		artistRepository.save(BadBunny);
		artistRepository.save(BadGyal);
		artistRepository.save(EmiliaMernes);

		Concert taylorConcert = new Concert("The Eras Tour Concert", "The biggest concert of Taylor Swift, featuring her greatest hits and new releases. This concert will take you on a journey through her musical career, with stunning visuals and unforgettable performances.", "2025-08-07", "20:00:00", "Madrid", 100, 50, List.of(TaylorSwift), "https://maps.app.goo.gl/YMYM7WxM2PubhQTSA");
		Concert oneDirectionConcert = new Concert("One Direction Reunion Tour", "The biggest concert of One Direction, reuniting all original members for an unforgettable night. Fans will enjoy a nostalgic trip with their favorite hits and new surprises.", "2025-08-16", "19:00:00", "Seville", 150, 75, List.of(HarryStyles, ZaynMalik, NiallHoran, LiamPayne, LouisTomlinson), "https://maps.app.goo.gl/X9Qv8msNY4qxL2me6");
		Concert shawnConcert = new Concert("Wonder Tour", "The biggest concert of Shawn Mendes, performing songs from his latest album and fan favorites. Expect a night full of energy, emotion, and incredible music.", "2025-12-25", "20:30:00", "Valencia", 110, 55, List.of(ShawnMendes), "https://maps.app.goo.gl/JESSSnWpFrn82t188");
		Concert dukiConcert = new Concert("The Ameri Concert", "The biggest concert of Duki, bringing the best of Trap music. This event promises to be a high-energy show with electrifying performances.", "2025-03-21", "22:00:00", "Bilbao", 130, 65, List.of(Duki), "https://maps.app.goo.gl/uyXfmJGoXCYLrMiM9");
		Concert taylorKanyeConcert = new Concert("The Galactic Duo", "A special concert featuring Taylor Swift and Kanye West, a unique collaboration of Pop and Hip-Hop. This once-in-a-lifetime event will showcase their greatest hits and new collaborations.", "2025-12-15", "21:00:00", "Madrid", 200, 100, List.of(TaylorSwift, KanyeWest), "https://maps.app.goo.gl/YMYM7WxM2PubhQTSA");
		Concert rauwConcert = new Concert("Cosa Nuestra", "The biggest concert of Rauw Alejandro, performing his latest hits and fan favorites. This event promises to be a night full of energy and unforgettable performances.", "2025-11-10", "21:00:00", "Malaga", 120, 60, List.of(RauwAlejandro), "https://maps.app.goo.gl/YMYM7WxM2PubhQTSA");
		Concert arianaConcert = new Concert("Sweetener World Tour", "The biggest concert of Ariana Grande, featuring her greatest hits and new releases. This concert will take you on a journey through her musical career, with stunning visuals and unforgettable performances.", "2025-09-10", "20:00:00", "Madrid", 140, 70, List.of(ArianaGrande), "https://maps.app.goo.gl/YMYM7WxM2PubhQTSA");
		Concert saikoConcert = new Concert("Saiko Tour", "The biggest concert of Saiko, performing his latest hits and fan favorites. This event promises to be a night full of energy and unforgettable performances.", "2025-10-05", "21:00:00", "Barcelona", 110, 55, List.of(Saiko), "https://maps.app.goo.gl/jR39U3coLSbxewF47");
		Concert bunnyConcert = new Concert("Bad Bunny World Tour", "The biggest concert of Bad Bunny, featuring his greatest hits and new releases. This concert will take you on a journey through his musical career, with stunning visuals and unforgettable performances.", "2025-11-20", "20:00:00", "Seville", 160, 80, List.of(BadBunny), "https://maps.app.goo.gl/X9Qv8msNY4qxL2me6");
		Concert gyalConcert = new Concert("Bad Gyal Tour", "The biggest concert of Bad Gyal, performing her latest hits and fan favorites. This event promises to be a night full of energy and unforgettable performances.", "2025-12-05", "21:00:00", "Valencia", 120, 60, List.of(BadGyal), "https://maps.app.goo.gl/JESSSnWpFrn82t188");
		Concert emiliaConcert = new Concert("Emilia Mernes Live", "The biggest concert of Emilia Mernes, performing her latest hits and fan favorites. This event promises to be a night full of energy and unforgettable performances.", "2025-12-20", "21:00:00", "Bilbao", 130, 65, List.of(EmiliaMernes), "https://maps.app.goo.gl/uyXfmJGoXCYLrMiM9");
		Concert karolConcert = new Concert("Karol G Tour", "The biggest concert of Karol G, performing her latest hits and fan favorites. This event promises to be a night full of energy and unforgettable performances.", "2025-07-15", "21:00:00", "Madrid", 150, 75, List.of(KarolG), "https://maps.app.goo.gl/YMYM7WxM2PubhQTSA");
		Concert aitanaConcert = new Concert("Aitana Tour", "The biggest concert of Aitana, performing her latest hits and fan favorites. This event promises to be a night full of energy and unforgettable performances.", "2025-08-20", "20:00:00", "Barcelona", 140, 70, List.of(Aitana), "https://maps.app.goo.gl/jR39U3coLSbxewF47");
		Concert latinFestival = new Concert("Latin Festival", "The biggest Latin music festival, featuring the best Latin artists in the world. This event promises to be a night full of energy, unforgettable performances, and a celebration of Latin culture.", "2025-09-30", "18:00:00", "Madrid", 250, 125, List.of(Duki, RauwAlejandro, Saiko, BadBunny, BadGyal, EmiliaMernes, Quevedo, KarolG), "https://maps.app.goo.gl/YMYM7WxM2PubhQTSA");
		userRepository.save(new User("John Doe", "user", 123456789, "user@example.com", passwordEncoder.encode("pass"), 20,"USER"));
		userRepository.save(new User("Admin User", "admin", 987654321, "admin@example.com", passwordEncoder.encode("admin"), 21,"USER","ADMIN"));
		setConcertImage(taylorConcert, "/static/images/Concerts/taylorswift.jpg");
		setConcertImage(oneDirectionConcert, "/static/images/Concerts/onedirection.jpg");
		setConcertImage(shawnConcert, "/static/images/Concerts/shawnmendes.jpg");
		setConcertImage(dukiConcert, "/static/images/Concerts/duki.jpg");		
		setConcertImage(rauwConcert, "/static/images/Concerts/rauwalejandro.jpg");
		setConcertImage(arianaConcert, "/static/images/Concerts/arianagrande.jpg");
		setConcertImage(saikoConcert, "/static/images/Concerts/saiko.jpg");	
		setConcertImage(bunnyConcert, "/static/images/Concerts/badbunny.jpg");
		setConcertImage(gyalConcert, "/static/images/Concerts/badgyal.jpg");
		setConcertImage(emiliaConcert, "/static/images/Concerts/emilia.jpg");
		setConcertImage(karolConcert, "/static/images/Concerts/karolg.jpg");
		setConcertImage(aitanaConcert, "/static/images/Concerts/aitana.jpg");
		setConcertImage(latinFestival, "/static/images/Concerts/latinfest.jpg");

		concertRepository.save(latinFestival);
		concertRepository.save(taylorConcert);
		concertRepository.save(oneDirectionConcert);
		concertRepository.save(shawnConcert);
		concertRepository.save(dukiConcert);
		concertRepository.save(taylorKanyeConcert);
		concertRepository.save(rauwConcert);
		concertRepository.save(arianaConcert);
		concertRepository.save(saikoConcert);
		concertRepository.save(bunnyConcert);
		concertRepository.save(gyalConcert);
		concertRepository.save(emiliaConcert);
		concertRepository.save(karolConcert);
		concertRepository.save(aitanaConcert);

	}

	public void setConcertImage(Concert concert, String classpathResource) throws IOException {
		concert.setConcertImage(true);
		Resource image = new ClassPathResource(classpathResource);
		concert.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
	}
}

