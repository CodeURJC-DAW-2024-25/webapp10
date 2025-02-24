package es.codeurjc.backend.model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String artistName;
    private String artistInfo;

    @ManyToMany(mappedBy = "concerts")
    private List<Artist> artists;

    @OneToMany(mappedBy = "concert")
    private List<Ticket> tickets;

    //Constructors
    public Concert(){}
    
    public Concert(String concertName, String artistName, String artistInfo, String concertDetails, Date concertDate, Time concertTime, String location){
        
        super();
        this.concertName = concertName;
        this.artistName = artistName;
        this.artistInfo = artistInfo;
        this.concertDetails = concertDetails;
        this.concertDate = concertDate;
        this.concertTime = concertTime;
        this.location = location;
    }

    //Getters and setters

    public String getConcertName() {
        return concertName;
    }

    public void setConcertName(String concertName) {
        this.concertName = concertName;
    }

    public String getArtistName() {
        return this.artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistInfo() {
        return this.artistInfo;
    }

    public void setArtistInfo(String artistInfo) {
        this.artistInfo = artistInfo;
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
}