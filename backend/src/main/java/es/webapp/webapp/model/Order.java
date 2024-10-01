package es.webapp.webapp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy="order")
    private List<ItemToBuy> itemsToBuy;

    @Column(name="totalCost")
    private Double totalCost;

    @Column(name="date")
    private Date creationDate;

    @Column(name="state")
    private State state;

    public enum State{
        PENDING, IN_PROGRESS, CONFIRMED, CANCELLED
    }

    public Order(){}

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }
    
    public void addItemToBuy(ItemToBuy itemToBuy){
        itemsToBuy.add(itemToBuy);
        itemToBuy.setOrder(this);
    }

    public void removeItemToBuy(ItemToBuy itemToBuy){
        itemsToBuy.remove(itemToBuy);
        itemToBuy.setOrder(null);
    }

    public void setTotalCost(Double totalCost){
        this.totalCost = totalCost;
    }

    public Double getTotalCost(){
        return totalCost;
    }

    public void setCreationDate(Date creationDate){
        this.creationDate = creationDate;
    }

    public Date getCreationDate(){
        return creationDate;
    }

    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return state;
    }
}
