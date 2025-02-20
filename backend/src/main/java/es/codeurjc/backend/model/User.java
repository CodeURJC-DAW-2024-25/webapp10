package es.codeurjc.backend.model;

import java.util.ArrayList;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "UserTable")
public class User {

    public enum userType{
        Admin,
        RegisteredUser
    }

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    private String fullName;
    private String userName;
    private String photo;
    private int phone;
    private String email;
    private String password;
    private int age; 
    private userType typeUser;
    private String DNI;
    private String favoriteGenre;
    private int TicketsBought;
    private ArrayList<Ticket> ticketsHistory;

    public User(){}

    public User (String fullName, String userName, String photo, int phone, String email, String password, int age, userType typeUser,  String DNI){
        super();
        this.fullName=fullName;
        this.userName=userName;
        this.photo=photo;
        this.phone=phone;
        this.email=email;
        this.password=password;
        this.age=age;
        this.typeUser=typeUser;
        this.TicketsBought=0;
        this.favoriteGenre=null;

    }

    public void setFullName(String fullName){
        this.fullName=fullName;
    }

    public void setUsername(String userName){
        this.userName=userName;
    }

    public void setPhoto(String photo){
        this.photo=photo;
    }

    public void setPhone(int phone){
        this.phone=phone;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public void setAge(int age){
        this.age=age;
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

    public int getPhone(){
        return this.phone;
    }

    public String getPhoto(){
        return this.photo;
    }

    public String getEmail(){
        return this.email;
    }

    public userType getUserType(){
        return this.typeUser;
    }

    public int getAge(){
        return this.age;
    }

    public boolean checkEqualPassWord(String passWord){
        
        if (this.password==passWord){
            return true;
        }
        
        return false;
    }


}
