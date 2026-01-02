package es.webapp.webapp.model;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
@Table(name = "tbl_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String code;

    @ManyToOne 
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "order")
    private List<ItemToBuy> itemToBuy = new ArrayList<>();


    @Column(name="totalCost")
    private Double totalCost;

    @Column(name="date")
    private String creationDate;//AAAA-MM-DD

    @Enumerated(EnumType.STRING)
    @Column(name="state")
    private State state;

    //private String auxState;

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

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public void addItemToBuy(ItemToBuy item){
        itemToBuy.add(item);
        item.setOrder(this);
    }

    public void removeItemToBuy(ItemToBuy item){
        itemToBuy.remove(item);
        item.setOrder(null);
    }

    public List<ItemToBuy> getItemToBuys(){
        return itemToBuy;
    }

    public void setTotalCost(Double totalCost){
        this.totalCost = totalCost;
    }

    public Double getTotalCost(){
        return totalCost;
    }

    public void setCreationDate(String creationDate){
        this.creationDate = creationDate;
    }

    public String getCreationDate(){
        return creationDate;
    }

    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return state;
    }
}
