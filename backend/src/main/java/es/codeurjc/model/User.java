package es.codeurjc.model;

import org.springframework.web.context.annotation.SessionScope;


@SessionScope
public class User {

    public enum userType{
        Admin,
        RegisteredUser
    }

    private String fullName;
    private String userName;
    private String photo;
    private int phone;
    private String email;
    private String password;
    private int age; 
    private userType typeUser;

    public User(){}

    public User (String fullName, String userName, String photo, int phone, String email, String password, int age, userType typeUser){
        this.fullName=fullName;
        this.userName=userName;
        this.photo=photo;
        this.phone=phone;
        this.email=email;
        this.password=password;
        this.age=age;
        this.typeUser=typeUser;
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
