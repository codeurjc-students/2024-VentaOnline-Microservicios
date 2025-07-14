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

    @OneToOne(mappedBy="shoppingCart")
    @JsonIgnore
    private User user;
    
    @Column(name = "totalCost")
    private Double totalCost = 0.0;

    @OneToMany(mappedBy="shoppingCart")
    private List<ItemToBuy> items = new ArrayList<>(); 

    public ShoppingCart() {}

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
        return totalCost;
    }

    public List<ItemToBuy> getItems() {
        return items;
    }
}
