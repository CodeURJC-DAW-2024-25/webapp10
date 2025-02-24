package es.codeurjc.backend.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Artist {
    
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    private String artistName;
    private String musicalStyle;
    private String artistInfo;

    @ManyToMany
    private List<Concert> concerts;

    //Constructors
    public Artist(){}

    public Artist (String artistName, String musicalStyle, String artisInfo) {
        this.artistName = artistName;
        this.musicalStyle = musicalStyle;
        this.artistInfo = artisInfo;
    }

    //Getters and setters
    
    public void setArtistName (String artistName){
        this.artistName = artistName;
    }

    public String getArtistName(){
            return artistName;
        }

    public void setMusicalStyle (String musicalStyle){
        this.musicalStyle = musicalStyle;
    }

    public String getMuscialStyle(){
            return musicalStyle;
        }

    public void setArtistInfo (String artistName){
        this.artistName = artistName;
    }

    public String getArtistInfo(){
        return artistInfo;
    }
}