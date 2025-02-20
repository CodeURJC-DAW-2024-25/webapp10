package es.codeurjc.backend.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Concert {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id = null;
    
    private String concertName; 
    private String artistName;
    private String artistInfo;
    private String concertDetails;
    private LocalDate concertDate;
    private LocalTime concertTime;
    private String location;

    //Constructors
    public Concert(){}
    
    public Concert(String concertName, String artistName, String artistInfo, String concertDetails, LocalDate concertDate, LocalTime concertTime, String location){
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

    public LocalDate getConcertDate() {
        return concertDate;
    }

    public void setConcertDate(LocalDate concertDate) {
        this.concertDate = concertDate;
    }

    public LocalTime getConcertTime() {
        return concertTime;
    }

    public void setConcertTime(LocalTime concertTime) {
        this.concertTime = concertTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}