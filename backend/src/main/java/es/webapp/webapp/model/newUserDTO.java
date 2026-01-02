package es.webapp.webapp.model;

import javax.persistence.Table;

@Table(name = "tbl_newUserDTO")
public class newUserDTO {

    private String username;
    private String name;
    private String email;
    private String password;
    private String rol;
    private String passwordConfirmation;
    private String code;
    private String street;
    private Integer number;
    private Integer zipCode;
    private String city;

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public String getRol(){
        return rol;
    }

    public void setRol(String rol){
        this.rol = rol;
    }

    public void setPasswordConfirmation(String passwordConfirmation){
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getPasswordConfirmation(){
        return passwordConfirmation;
    }

    public void setCode(String code){
        this.code=code;
    }

    public String getCode(){
        return code;
    }

    public void setStreet(String street){
        this.street=street;
    }

    public String getStreet(){
        return street;
    }

    public void setNumber(Integer number){
        this.number=number;
    }

    public Integer getNumber(){
        return number;
    }

    public void setZipCode(Integer zipCode){
        this.zipCode=zipCode;
    }

    public Integer getZipCode(){
        return zipCode;
    }

    public void setCity(String city){
        this.city=city;
    }

    public String getCity(){
        return city;
    }
}
