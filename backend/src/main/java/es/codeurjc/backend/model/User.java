package es.codeurjc.backend.model;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	private Long id=null;

    private String fullName;
    private String userName;
    private Integer phone;
    private String email;
    private String encodedPassword;
    private Integer age;

    @Lob
    private Blob profilePhoto;
    private boolean image;

    @OneToMany(mappedBy = "userOwner", fetch = FetchType.LAZY)
    private List<Ticket> tickets; 

    @ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;

    public User(){}

    public User (String fullName, String userName, Integer phone, String email, String encodedPassword, Integer age, String... roles){
        
        super();
        this.fullName=fullName;
        this.userName=userName;
        this.phone=phone;
        this.email=email;
        this.encodedPassword=encodedPassword;
        this.age=age;
        this.roles = List.of(roles);
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

    public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
