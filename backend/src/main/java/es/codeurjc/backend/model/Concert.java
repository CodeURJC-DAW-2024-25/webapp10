package es.codeurjc.backend.model;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

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
    private Date concertDate;
    private Time concertTime;
    private String location;
    private Integer stadiumPrice;
    private Integer trackPrice;

    @Lob
    private Blob concertImage;

    @ManyToMany(mappedBy = "concerts")
    private List<Artist> artists;

    @OneToMany(mappedBy = "concert")
    private List<Ticket> tickets;

    //Constructors
    public Concert(){}
    
    public Concert(String concertName, String concertDetails, Date concertDate, Time concertTime, String location, Integer stadiumPrice, Integer trackPrice, List<Artist> artists) {
        
        super();
        this.concertName = concertName;
        this.concertDetails = concertDetails;
        this.concertDate = concertDate;
        this.concertTime = concertTime;
        this.location = location;
        this.artists = artists;
        this.stadiumPrice = stadiumPrice;
        this.trackPrice = trackPrice;
    }

    //Getters and setters

    public String getConcertName() {
        return concertName;
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

    public Date getConcertDate() {
        return concertDate;
    }

    public void setConcertDate(Date concertDate) {
        this.concertDate = concertDate;
    }

    public Time getConcertTime() {
        return concertTime;
    }

    public void setConcertTime(Time concertTime) {
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

	public void setId(long id) {
		this.id = id;
	}

    public Blob getConcertImage() {
        return concertImage;
    }

    public void setConcertImage(Blob concertImage) {
        this.concertImage = concertImage;
    }

    public List<Artist> getArtists() {
        return artists;
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
}