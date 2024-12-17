package es.webapp.webapp.model;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "tbl_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne 
    private User user;

    @Column(name="totalCost")
    private Double totalCost;

    @Column(name="date")
    private LocalDate creationDate;

    @Column(name="state")
    private State state;

    private String auxState;

    public Order(){}

    public enum State {
        PENDING, CONFIRMED, DELIVERED, CANCELLED
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public void setTotalCost(Double totalCost){
        this.totalCost = totalCost;
    }

    public Double getTotalCost(){
        return totalCost;
    }

    public void setCreationDate(LocalDate creationDate){
        this.creationDate = creationDate;
    }

    public LocalDate getCreationDate(){
        return creationDate;
    }

    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return state;
    }

    public void setAuxState(String state){
        this.auxState = state;
    }

    public String getAuxState(){
        return auxState;
    }
}
