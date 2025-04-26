package es.codeurjc.backend.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.backend.dto.concert.ConcertDTO;
import es.codeurjc.backend.dto.ticket.TicketDTO;
import es.codeurjc.backend.dto.ticket.TicketMapper;
import es.codeurjc.backend.model.Ticket;
import es.codeurjc.backend.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	private TicketRepository repository;

	@Autowired
    private ConcertService concertService;

	@Autowired
	private TicketMapper mapper;

	public Collection<TicketDTO> getTickets() {
		return toDTOs(repository.findAll());
	}

	public TicketDTO getTicket(long id) {
		return toDTO(repository.findById(id).orElseThrow());
	}

	public TicketDTO deleteTicket(long id) {

		Ticket Ticket = repository.findById(id).orElseThrow();
		TicketDTO TicketDTO = toDTO(Ticket);

		repository.deleteById(id);

		return TicketDTO;
	}

	public TicketDTO createTicket(TicketDTO ticketDTO) {

		if (ticketDTO == null ||ticketDTO.id() != null) {
			throw new IllegalArgumentException();
		}
		Ticket ticket = toDomain(ticketDTO);
		repository.save(ticket);

		return toDTO(ticket);
	}

	public TicketDTO replaceTicket(long id, TicketDTO updateTicketDTO) throws SQLException {

		if (repository.existsById(id)) {
			Ticket updatedTicket = toDomain(updateTicketDTO);
			updatedTicket.setId(id);
			repository.save(updatedTicket);
			return toDTO(updatedTicket);
		} else {
			throw new NoSuchElementException();
		}

	}

	private TicketDTO toDTO(Ticket Ticket) {
		return mapper.toDTO(Ticket);
	}

	private Ticket toDomain(TicketDTO ticketDTO) {
		return mapper.toDomain(ticketDTO);
	}

	private Collection<TicketDTO> toDTOs(Collection<Ticket> Tickets) {
		return mapper.toDTOs(Tickets);
	}

	public byte[] generateTicketPdf(List<TicketDTO> tickets) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                PDColor titleColor = new PDColor(new float[]{75 / 255f, 0 / 255f, 130 / 255f}, PDDeviceRGB.INSTANCE);
                contentStream.setNonStrokingColor(titleColor);

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 750);
                contentStream.showText("Ticket Purchase History - TicketZone Fest");
                contentStream.endText();

                int yPosition = 700;
                for (TicketDTO ticket : tickets) {
                    ConcertDTO concertDTO = concertService.getConcert(ticket.concertId());
                    PDColor concertNameColor = new PDColor(new float[]{84 / 255f, 26 / 255f, 113 / 255f}, PDDeviceRGB.INSTANCE);
                    contentStream.setNonStrokingColor(concertNameColor);

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText("Concert: " + concertDTO.concertName());
                    contentStream.endText();

                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.beginText();
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

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }


	public TicketDTO createOrReplaceTicket(Long id, TicketDTO ticketDTO) throws SQLException {
		
		TicketDTO ticket;
		if(id == null) {
			ticket = createTicket(ticketDTO);
		} else {
			ticket = replaceTicket(id, ticketDTO);
		}
		return ticket;
	}

	public Integer calculateTicketPrice(String ticketType, ConcertDTO concertDTO) {
    if ("stadiumStand".equals(ticketType)) {
        return concertDTO.stadiumPrice();
    } else if ("concertTrack".equals(ticketType)) {
        return concertDTO.trackPrice();
    }
    return 0;
	}

}
