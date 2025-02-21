package es.codeurjc.backend.model;

import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "concerts")
public class Concert {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    private String concertName; 
    private String artistName;
    private String artistInfo;
    private String concertDetails;

    @Column(columnDefinition = "DATE")
    private Date concertDate;

    @Column(columnDefinition = "TIME")
    private Time concertTime;
    private String location;

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
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistInfo() {
        return artistInfo;
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