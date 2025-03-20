package es.codeurjc.backend.model;

import java.sql.Blob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Entity(name = "UserTable")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    private String fullName;
    private String userName;
    private Integer phone;
    private String email;
    private String encodedPassword;
    private Integer age;
    private Integer numTicketsBought = 0;
    private String favoriteGenre;

    @Lob
    private Blob profilePhoto;
    private boolean image;

    @OneToMany(mappedBy = "userOwner", fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    public User() {
        this.numTicketsBought = 0; 
    }

    public User(String fullName, String userName, Integer phone, String email, String encodedPassword, Integer age, String... roles) {
        this.fullName = fullName;
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.age = age;
        this.image = false;
        this.numTicketsBought = 0; 
        this.roles = List.of(roles);
        this.favoriteGenre = "None";
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public boolean getImage(){
		return this.image;
	}

	public void setImage(boolean image){
		this.image = image;
	}
    
    public String getFullName(){
        return this.fullName;
    }

    public void setFullName(String fullName){
        this.fullName=fullName;
    }

    public String getUserName(){
        return this.userName;
    }

    public void setFavoriteGenre(String favoriteGenre){
        this.favoriteGenre=favoriteGenre;
    }

    public void addFavoriteGenre(){
        
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (Ticket ticket : tickets) {
            Concert concert = ticket.getConcert();
            if (concert == null) continue; 
            for (Artist artist : concert.getArtists()) {
                frequencyMap.put(artist.getMusicalStyle(), frequencyMap.getOrDefault(artist.getMusicalStyle(), 0) + 1);
            }
        }
    
        if (frequencyMap.isEmpty()) {
            this.favoriteGenre = "None"; 
            return;
        }
    
        this.favoriteGenre = frequencyMap.entrySet()
                          .stream()
                          .max(Map.Entry.comparingByValue())
                          .map(Map.Entry::getKey)
                          .orElse("None");

        
    }

    public String getFavoriteGenre(){
        return this.favoriteGenre;
    }

    public void setNumTicketsBought(Integer num){
        if (this.numTicketsBought == null) {
            this.numTicketsBought = 0;
        }else{
            this.numTicketsBought += num;
        }
        
    }

    public Integer getNumTicketsBought(){
        return this.numTicketsBought;
    }

    public void setUserName(String userName){
        this.userName=userName;
    }

    public Integer getPhone(){
        return this.phone;
    }

    public void setPhone(Integer phone){
        this.phone=phone;
    }

    public String getEmail(){
        return this.email;
    }
    
    public void setEmail(String email){
        this.email=email;
    }

    public String getEncodedPassword(){
        return this.encodedPassword;
    }

    public void setEncodedPassword(String password){
        this.encodedPassword=password;
    }

    public Integer getAge(){
        return this.age;
    }

    public void setAge(Integer age){
        if (age==null || age <0){
            this.age=0;
        }else{
            this.age=age;
        }
    }

    public Blob getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(Blob profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> ticketsHistory) {
        this.tickets = ticketsHistory;
    }

    public void addTickets(Ticket ticket){
        this.tickets.add(ticket);
    }

    public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
