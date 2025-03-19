package es.codeurjc.backend.service;

import java.io.IOException;
import java.net.URISyntaxException;
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
import es.codeurjc.backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Service
public class DatabaseInitializer {

	@Autowired
	private ArtistRepository artistRepository;

	@Autowired
	private ConcertRepository concertRepository;

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
		Artist Mora = new Artist("Mora", "Trap", "One of the best Reggaeton artists in the world");

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
		artistRepository.save(Quevedo);
		artistRepository.save(Mora);

		Concert taylorConcert = new Concert("The Eras Tour Concert", "The biggest concert of Taylor Swift, featuring her greatest hits and new releases. This concert will take you on a journey through her musical career, with stunning visuals and unforgettable performances.", "2025-08-07", "20:00:00", "Madrid", 100, 50, List.of(TaylorSwift), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d1518.0022199896212!2d-3.6894281!3d40.4530387!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd4228e23705d39f%3A0xa8fff6d26e2b1988!2sEstadio%20Santiago%20Bernab%C3%A9u!5e0!3m2!1ses!2ses!4v1740848155580!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert oneDirectionConcert = new Concert("One Direction Reunion Tour", "The biggest concert of One Direction, reuniting all original members for an unforgettable night. Fans will enjoy a nostalgic trip with their favorite hits and new surprises.", "2025-08-16", "19:00:00", "Seville", 150, 75, List.of(HarryStyles, ZaynMalik, NiallHoran, LiamPayne, LouisTomlinson), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d6340.4859060870185!2d-5.9709778!3d37.3840863!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd126ebd0479b657%3A0x62ff222e3173860!2zRXN0YWRpbyBSYW3Ds24gU8OhbmNoZXogUGl6anXDoW4!5e0!3m2!1ses!2ses!4v1740848384097!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert shawnConcert = new Concert("Wonder Tour", "The biggest concert of Shawn Mendes, performing songs from his latest album and fan favorites. Expect a night full of energy, emotion, and incredible music.", "2025-12-25", "20:30:00", "Valencia", 110, 55, List.of(ShawnMendes), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d6159.528385597443!2d-0.35859019999999997!3d39.4746556!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd6048bd2129f74b%3A0x7504540b8de53e49!2sEstadio%20de%20Mestalla!5e0!3m2!1ses!2ses!4v1740848318823!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert dukiConcert = new Concert("The Ameri Concert", "The biggest concert of Duki, bringing the best of Trap music. This event promises to be a high-energy show with electrifying performances.", "2025-03-21", "22:00:00", "Bilbao", 130, 65, List.of(Duki), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d5810.783256814313!2d-2.9493730999999994!3d43.2641706!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd4e503004259e4f%3A0xdd63774224e34644!2sEstadio%20de%20San%20Mam%C3%A9s!5e0!3m2!1ses!2ses!4v1740848223944!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert taylorKanyeConcert = new Concert("The Galactic Duo", "A special concert featuring Taylor Swift and Kanye West, a unique collaboration of Pop and Hip-Hop. This once-in-a-lifetime event will showcase their greatest hits and new collaborations.", "2025-12-15", "21:00:00", "Madrid", 200, 100, List.of(TaylorSwift, KanyeWest), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d1518.0022199896212!2d-3.6894281!3d40.4530387!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd4228e23705d39f%3A0xa8fff6d26e2b1988!2sEstadio%20Santiago%20Bernab%C3%A9u!5e0!3m2!1ses!2ses!4v1740848155580!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert rauwConcert = new Concert("Cosa Nuestra", "The biggest concert of Rauw Alejandro, performing his latest hits and fan favorites. This event promises to be a night full of energy and unforgettable performances.", "2025-11-10", "21:00:00", "Malaga", 120, 60, List.of(RauwAlejandro), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3197.541135594574!2d-4.429226924503056!3d36.733580172266265!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd72f7b04fcb2363%3A0x69f9e825cde5bd60!2sEstadio%20La%20Rosaleda!5e0!3m2!1ses!2ses!4v1740848356087!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert arianaConcert = new Concert("Sweetener World Tour", "The biggest concert of Ariana Grande, featuring her greatest hits and new releases. This concert will take you on a journey through her musical career, with stunning visuals and unforgettable performances.", "2025-09-10", "20:00:00", "Madrid", 140, 70, List.of(ArianaGrande), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d1518.0022199896212!2d-3.6894281!3d40.4530387!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd4228e23705d39f%3A0xa8fff6d26e2b1988!2sEstadio%20Santiago%20Bernab%C3%A9u!5e0!3m2!1ses!2ses!4v1740848155580!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert saikoConcert = new Concert("Saiko Tour", "The biggest concert of Saiko, performing his latest hits and fan favorites. This event promises to be a night full of energy and unforgettable performances.", "2025-10-05", "21:00:00", "Barcelona", 110, 55, List.of(Saiko), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2994.4659881265447!2d2.1520405!3d41.3639595!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x12a4a26c35ec4be9%3A0x9e9035c097bdfdf4!2sPalau%20Sant%20Jordi!5e0!3m2!1ses!2ses!4v1740848282833!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert bunnyConcert = new Concert("Bad Bunny World Tour", "The biggest concert of Bad Bunny, featuring his greatest hits and new releases. This concert will take you on a journey through his musical career, with stunning visuals and unforgettable performances.", "2025-11-20", "20:00:00", "Seville", 160, 80, List.of(BadBunny), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d6340.4859060870185!2d-5.9709778!3d37.3840863!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd126ebd0479b657%3A0x62ff222e3173860!2zRXN0YWRpbyBSYW3Ds24gU8OhbmNoZXogUGl6anXDoW4!5e0!3m2!1ses!2ses!4v1740848384097!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert gyalConcert = new Concert("Bad Gyal Tour", "The biggest concert of Bad Gyal, performing her latest hits and fan favorites. This event promises to be a night full of energy and unforgettable performances.", "2025-12-05", "21:00:00", "Valencia", 120, 60, List.of(BadGyal), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d6159.528385597443!2d-0.35859019999999997!3d39.4746556!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd6048bd2129f74b%3A0x7504540b8de53e49!2sEstadio%20de%20Mestalla!5e0!3m2!1ses!2ses!4v1740848318823!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert emiliaConcert = new Concert("Emilia Mernes Live", "The biggest concert of Emilia Mernes, performing her latest hits and fan favorites. This event promises to be a night full of energy and unforgettable performances.", "2025-12-20", "21:00:00", "Bilbao", 130, 65, List.of(EmiliaMernes), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d5810.783256814313!2d-2.9493730999999994!3d43.2641706!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd4e503004259e4f%3A0xdd63774224e34644!2sEstadio%20de%20San%20Mam%C3%A9s!5e0!3m2!1ses!2ses!4v1740848223944!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert karolConcert = new Concert("Karol G Tour", "The biggest concert of Karol G, performing her latest hits and fan favorites. This event promises to be a night full of energy and unforgettable performances.", "2025-07-15", "21:00:00", "Madrid", 150, 75, List.of(KarolG), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d1518.0022199896212!2d-3.6894281!3d40.4530387!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd4228e23705d39f%3A0xa8fff6d26e2b1988!2sEstadio%20Santiago%20Bernab%C3%A9u!5e0!3m2!1ses!2ses!4v1740848155580!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert aitanaConcert = new Concert("Aitana Tour", "The biggest concert of Aitana, performing her latest hits and fan favorites. This event promises to be a night full of energy and unforgettable performances.", "2025-08-20", "20:00:00", "Barcelona", 140, 70, List.of(Aitana), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2994.4659881265447!2d2.1520405!3d41.3639595!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x12a4a26c35ec4be9%3A0x9e9035c097bdfdf4!2sPalau%20Sant%20Jordi!5e0!3m2!1ses!2ses!4v1740848282833!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert latinFestival = new Concert("Latin Festival", "The biggest Latin music festival, featuring the best Latin artists in the world. This event promises to be a night full of energy, unforgettable performances, and a celebration of Latin culture.", "2025-09-30", "18:00:00", "Madrid", 250, 125, List.of(Duki, RauwAlejandro, Saiko, BadBunny, BadGyal, EmiliaMernes, Quevedo, KarolG), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d1518.0022199896212!2d-3.6894281!3d40.4530387!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd4228e23705d39f%3A0xa8fff6d26e2b1988!2sEstadio%20Santiago%20Bernab%C3%A9u!5e0!3m2!1ses!2ses!4v1740848155580!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert harryConcert = new Concert("Harry Styles Live", "The biggest concert of Harry Styles, featuring his greatest hits and new releases. This concert will take you on a journey through his musical career, with stunning visuals and unforgettable performances.", "2025-07-10", "20:00:00", "Madrid", 120, 60, List.of(HarryStyles), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d1518.0022199896212!2d-3.6894281!3d40.4530387!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd4228e23705d39f%3A0xa8fff6d26e2b1988!2sEstadio%20Santiago%20Bernab%C3%A9u!5e0!3m2!1ses!2ses!4v1740848155580!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert louisConcert = new Concert("Louis Tomlinson Live", "The biggest concert of Louis Tomlinson, featuring his greatest hits and new releases. This concert will take you on a journey through his musical career, with stunning visuals and unforgettable performances.", "2025-07-15", "20:00:00", "Barcelona", 110, 55, List.of(LouisTomlinson), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2994.4659881265447!2d2.1520405!3d41.3639595!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x12a4a26c35ec4be9%3A0x9e9035c097bdfdf4!2sPalau%20Sant%20Jordi!5e0!3m2!1ses!2ses!4v1740848282833!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert zaynConcert = new Concert("Zayn Malik Live", "The biggest concert of Zayn Malik, featuring his greatest hits and new releases. This concert will take you on a journey through his musical career, with stunning visuals and unforgettable performances.", "2025-07-20", "20:00:00", "Seville", 130, 65, List.of(ZaynMalik), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d6340.4859060870185!2d-5.9709778!3d37.3840863!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd126ebd0479b657%3A0x62ff222e3173860!2zRXN0YWRpbyBSYW3Ds24gU8OhbmNoZXogUGl6anXDoW4!5e0!3m2!1ses!2ses!4v1740848384097!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert niallConcert = new Concert("Niall Horan Live", "The biggest concert of Niall Horan, featuring his greatest hits and new releases. This concert will take you on a journey through his musical career, with stunning visuals and unforgettable performances.", "2025-07-25", "20:00:00", "Valencia", 120, 60, List.of(NiallHoran), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d6159.528385597443!2d-0.35859019999999997!3d39.4746556!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd6048bd2129f74b%3A0x7504540b8de53e49!2sEstadio%20de%20Mestalla!5e0!3m2!1ses!2ses!4v1740848318823!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert liamConcert = new Concert("Liam Payne Live", "The biggest concert of Liam Payne, featuring his greatest hits and new releases. This concert will take you on a journey through his musical career, with stunning visuals and unforgettable performances.", "2025-07-30", "20:00:00", "Bilbao", 130, 65, List.of(LiamPayne), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d5810.783256814313!2d-2.9493730999999994!3d43.2641706!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd4e503004259e4f%3A0xdd63774224e34644!2sEstadio%20de%20San%20Mam%C3%A9s!5e0!3m2!1ses!2ses!4v1740848223944!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert moraConcert = new Concert("Mora Live", "The biggest concert of Mora, featuring his greatest hits and new releases. This concert will take you on a journey through his musical career, with stunning visuals and unforgettable performances.", "2025-08-05", "20:00:00", "Madrid", 120, 60, List.of(Mora), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d1518.0022199896212!2d-3.6894281!3d40.4530387!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd4228e23705d39f%3A0xa8fff6d26e2b1988!2sEstadio%20Santiago%20Bernab%C3%A9u!5e0!3m2!1ses!2ses!4v1740848155580!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert quevedoConcert = new Concert("Quevedo Live", "The biggest concert of Quevedo, featuring his greatest hits and new releases. This concert will take you on a journey through his musical career, with stunning visuals and unforgettable performances.", "2025-08-10", "20:00:00", "Barcelona", 110, 55, List.of(Quevedo), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2994.4659881265447!2d2.1520405!3d41.3639595!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x12a4a26c35ec4be9%3A0x9e9035c097bdfdf4!2sPalau%20Sant%20Jordi!5e0!3m2!1ses!2ses!4v1740848282833!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
		Concert kanyeConcert = new Concert("Yeezus Tour", "The biggest concert of Kanye West, featuring his greatest hits and new releases. This concert will take you on a journey through his musical career, with stunning visuals and unforgettable performances.", "2025-09-15", "21:00:00", "Barcelona", 150, 75, List.of(KanyeWest), "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2994.4659881265447!2d2.1520405!3d41.3639595!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x12a4a26c35ec4be9%3A0x9e9035c097bdfdf4!2sPalau%20Sant%20Jordi!5e0!3m2!1ses!2ses!4v1740848282833!5m2!1ses!2ses\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");

		userRepository.save(new User("Registered User", "user", 123456789, "user@example.com", passwordEncoder.encode("user"), 20,"USER"));
		userRepository.save(new User("Admin User", "admin", 987654321, "admin@example.com", passwordEncoder.encode("admin"), 70,"USER","ADMIN"));
		
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
		setConcertImage(taylorKanyeConcert, "/static/images/Concerts/taylorkanye.jpg");
		setConcertImage(harryConcert, "/static/images/Concerts/harrystyles.jpg");
		setConcertImage(louisConcert, "/static/images/Concerts/louistomlinson.jpg");
		setConcertImage(zaynConcert, "/static/images/Concerts/zaynmalik.jpg");
		setConcertImage(niallConcert, "/static/images/Concerts/niallhoran.jpg");
		setConcertImage(liamConcert, "/static/images/Concerts/liampayne.jpg");
		setConcertImage(moraConcert, "/static/images/Concerts/mora.jpg");
		setConcertImage(quevedoConcert, "/static/images/Concerts/quevedo.jpg");
		setConcertImage(kanyeConcert, "/static/images/Concerts/kanyewest.jpg");

		concertRepository.save(bunnyConcert);
		concertRepository.save(arianaConcert);
		concertRepository.save(rauwConcert);
		concertRepository.save(liamConcert);
		concertRepository.save(saikoConcert);
		concertRepository.save(gyalConcert);
		concertRepository.save(emiliaConcert);
		concertRepository.save(aitanaConcert);
		concertRepository.save(shawnConcert);
		concertRepository.save(dukiConcert);
		concertRepository.save(taylorKanyeConcert);
		concertRepository.save(karolConcert);
		concertRepository.save(taylorConcert);
		concertRepository.save(oneDirectionConcert);
		concertRepository.save(harryConcert);
		concertRepository.save(louisConcert);
		concertRepository.save(zaynConcert);
		concertRepository.save(niallConcert);
		concertRepository.save(moraConcert);
		concertRepository.save(quevedoConcert);
		concertRepository.save(kanyeConcert);
		concertRepository.save(latinFestival);

	}

	public void setConcertImage(Concert concert, String classpathResource) throws IOException {
		concert.setConcertImage(true);
		Resource image = new ClassPathResource(classpathResource);
		concert.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
	}
}

