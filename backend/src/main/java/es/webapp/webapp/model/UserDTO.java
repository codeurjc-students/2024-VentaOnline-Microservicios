package es.webapp.webapp.model;

import javax.persistence.Table;

@Table(name = "tbl_userDTO")
public class UserDTO {

    private String name;
    private String email;
    private String code;
    private String street;
    private Integer number;
    private Integer zipCode;
    private String city;


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
