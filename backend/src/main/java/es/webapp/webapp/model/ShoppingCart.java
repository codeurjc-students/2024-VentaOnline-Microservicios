package es.webapp.webapp.model;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tbl_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String code;

    @JsonIgnore
    @OneToOne(mappedBy="shoppingCart")
    private User user;
    
    @Column(name = "totalCost")
    private Double totalCost = 0.0;

    @Column(name="time")
    private String buyTime;

    @OneToMany(mappedBy="shoppingCart")
    private List<ItemToBuy> items = new ArrayList<>(); 

    public ShoppingCart() {}

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public void setCode(String code){
        this.code=code;
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
    
    public void addItemToBuy(ItemToBuy itemToBuy){
        items.add(itemToBuy);
        itemToBuy.setShoppingCart(this);
    }

    public void removeItemToBuy(ItemToBuy itemToBuy){
        items.remove(itemToBuy);
        itemToBuy.setShoppingCart(null);
    }

    public void setTotalCost(Double totalCost){
        this.totalCost = totalCost;
    }

    public Double getTotalCost(){
        return ((double)Math.round(totalCost * 100d) / 100d);
    }

    public List<ItemToBuy> getItems() {
        return items;
    }

    public void setBuyTime(String time){
        this.buyTime = time;
    }

    public String getBuyTime(){
        return buyTime;
    }
}
