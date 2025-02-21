package es.codeurjc.backend.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import jakarta.annotation.PostConstruct;

import org.hibernate.engine.jdbc.BlobProxy;
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

	}
}
