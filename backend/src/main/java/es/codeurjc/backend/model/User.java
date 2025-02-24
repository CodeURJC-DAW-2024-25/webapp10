package es.codeurjc.backend.model;

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
import jakarta.persistence.OneToMany;

@Entity(name = "UserTable")
public class User {


    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id=null;

    private String fullName;
    private String userName;
    private String photo;
    private Integer phone;
    private String email;
    private String password;
    private Integer age; 
    private Integer TicketsBought;

    @OneToMany(mappedBy = "userOwner", fetch = FetchType.LAZY)
    private List<Ticket> ticketsHistory; 

    @ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;


    public User(){}

    public User (String fullName, String userName, String photo, Integer phone, String email, String password, Integer age, String... roles){
        
        super();
        this.fullName=fullName;
        this.userName=userName;
        this.photo=photo;
        this.phone=phone;
        this.email=email;
        this.password=password;
        this.age=age;
        this.TicketsBought=0;
        this.roles = List.of(roles);
        

    }

    public void setFullName(String fullName){
        this.fullName=fullName;
    }

    public Long getId(){
        return this.id;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }

    public void setPhoto(String photo){
        this.photo=photo;
    }

    

    public void setTicketsBought(Integer tickets){
        this.TicketsBought=tickets;
    }

    public void setPhone(Integer phone){
        this.phone=phone;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public void setAge(Integer age){
        if (age==null || age <0){
            this.age=0;
        }else{
            this.age=age;
        }
    }

    public String getFullName(){
        return this.fullName;
    }

    public String getUserName(){
        return this.userName;
    }

    public String getPassword(){
        return this.password;
    }

    public Integer getPhone(){
        return this.phone;
    }

    public String getPhoto(){
        return this.photo;
    }

    public String getEmail(){
        return this.email;
    }

    public Integer getAge(){
        return this.age;
    }

    public boolean checkEqualPassWord(String passWord){
        
        if (this.password==passWord){
            return true;
        }
        
        return false;
    }

    public Integer getTicketsBought(){
        return this.TicketsBought;
    }

    public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

    public List<Ticket> getTickets() {
		return ticketsHistory;
	}

	public void setTickets(List<Ticket> tickets) {
		this.ticketsHistory = tickets;
	}

}
