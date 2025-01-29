package es.webapp.webapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tbl_direction")
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;
    
    @Column(name = "zipCode")
    private Integer zipCode;

    @Column(name = "city")
    private String city;

    
    @JsonIgnore
    @OneToOne(mappedBy = "direction")
    private User user;

    public Direction(){}

    public Direction(String street, Integer number, Integer zipCode, String city){
        super();
        this.street=street;
        this.number=number;
        this.zipCode=zipCode;
        this.city=city;
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

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }
}
