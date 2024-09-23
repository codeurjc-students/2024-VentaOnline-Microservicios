package es.webapp.webapp.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_user")
public class User {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String encodedPassword;

    @Column(name = "rol")
    private String rol;

    private String passwordConfirmation;

    @Lob
    //@Type(type = "org.hibernate.type.ImageType")
    @Column(name = "image")
    private Blob avatar;

    public User(){}

    public User(String email, String encodedPassword, String roles){
        this.email=email;
        this.encodedPassword=encodedPassword;
        this.rol=roles;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
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

    public void setEncodedPassword(String password){
        this.encodedPassword = password;
    }

    public String getEncodedPassword(){
        return encodedPassword;
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

    public void setImageFile(Blob image){
        this.avatar = image;
    }

    public Blob getImageFile(){
        return avatar;
    }
}
