package es.webapp.webapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemToBuy {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Item item;

    private String size;

    private Integer count;

    @ManyToOne
    @JsonIgnore
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JsonIgnore
    private  Order order;

    public ItemToBuy() {}

    public void setId(Long id){
        this.id=id;
    }

    public Long getId(){
        return id;
    }

    public String getSize(){
        return size;
    }

    public void setSize(String size){
        this.size = size;
    }

    public void setItem(Item item){
        this.item=item;
    }

    public Item getItem(){
        return item;
    }

    public void setCount(Integer count){
        this.count=count;
    }

    public Integer getCount(){
        return count;
    }

    public void setShoppingCart(ShoppingCart shoppingCart){
        this.shoppingCart= shoppingCart;
    }

    public ShoppingCart getShoppingCart(){
        return shoppingCart;
    }

    public void setOrder(Order order){
        this.order=order;
    }

    public Order getOrder(){
        return order;
    }    
}
