package es.codeurjc.model;

import org.springframework.web.context.annotation.SessionScope;

@SessionScope
public class User {

    private String fullName;
    private String userName;
    private String photo;
    private int phone;
    private String email;
    private String password;
    private int age; 

    public User (String fullName, String userName, String photo, int phone, String email, String password, int age){
        this.fullName=fullName;
        this.userName=userName;
        this.photo=photo;
        this.phone=phone;
        this.email=email;
        this.password=password;
        this.age=age;
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
        this.fullName=fullName;
    }

}
