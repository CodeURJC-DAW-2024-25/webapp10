package es.codeurjc.model;

public class Artist {
    
    private String artistName;
    private String musicalStyle;
    private String artistInfo;

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