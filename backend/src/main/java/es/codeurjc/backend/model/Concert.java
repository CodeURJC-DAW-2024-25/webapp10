package es.codeurjc.backend.model;

import java.sql.Blob;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "concerts")
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String concertName;
    private String concertDetails;
    private String concertDate;
    private String concertTime;
    private String location;
    private String color;
    private Integer stadiumPrice;
    private Integer trackPrice;

    @Column(columnDefinition = "TEXT")
    private String map;

    @Lob
    private Blob imageFile;

    private boolean concertImage;

    @ManyToMany
    private List<Artist> artists;

    @OneToMany(mappedBy = "concert")
    private List<Ticket> tickets;

    // Constructors
    public Concert() {
    }

    public Concert(String concertName, String concertDetails, String concertDate, String concertTime, String location,
            Integer stadiumPrice, Integer trackPrice, List<Artist> artists, String map) {

        super();
        this.concertName = concertName;
        this.concertDetails = concertDetails;
        this.concertDate = concertDate;
        this.concertTime = concertTime;
        this.location = location;
        this.artists = artists;
        this.stadiumPrice = stadiumPrice;
        this.trackPrice = trackPrice;
        this.map = map;
    }

    // Getters and setters

    public String getConcertName() {
        return concertName;
    }

    public void addTickets(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public void setConcertName(String concertName) {
        this.concertName = concertName;
    }

    public String getConcertDetails() {
        return concertDetails;
    }

    public void setConcertDetails(String concertDetails) {
        this.concertDetails = concertDetails;
    }

    public String getConcertDate() {
        return concertDate;
    }

    public void setConcertDate(String concertDate) {
        this.concertDate = concertDate;
    }

    public String getConcertTime() {
        return concertTime;
    }

    public void setConcertTime(String concertTime) {
        this.concertTime = concertTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getConcertImage() {
        return this.concertImage;
    }

    public void setConcertImage(boolean concertImage) {
        this.concertImage = concertImage;
    }

    public List<Artist> getArtists() {
        return this.artists;
    }

    public Blob getImageFile() {
        return imageFile;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public void setImageFile(Blob image) {
        this.imageFile = image;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Integer getStadiumPrice() {
        return stadiumPrice;
    }

    public void setStadiumPrice(Integer stadiumPrice) {
        this.stadiumPrice = stadiumPrice;
    }

    public Integer getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(Integer trackPrice) {
        this.trackPrice = trackPrice;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getColor() {
        return color;
    }

    public int countTicketsSold() {
        int totalTickets = 0;

        for (Ticket ticket : tickets) {
            totalTickets += ticket.getNumTickets();
        }

        return totalTickets;
    }
}