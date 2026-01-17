package es.webapp.webapp.model;

import java.sql.Blob;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tbl_item")   
public class Item {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; 

    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Lob
    @Column(name = "image")
    private Blob itemImage;

    @Column(name = "gender")
    private String gender;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Stock<?>> itemStocks = new ArrayList<>();

    @ManyToMany
    @JsonIgnore
    private List<ItemToBuy> itemsToBuy = new ArrayList<>();

    @ManyToMany
    @JsonIgnore
    private List<User> users = new ArrayList<>();


    public Item(){
    }


    public void setStock(Stock<?> stock){
        itemStocks.add(stock);
        stock.setItem(this);
    }

    public void removeStock(Stock<?> stock){
        itemStocks.remove(stock);
        stock.setItem(null);
    }

    public List<Stock<?>> getStocks(){
        return itemStocks;
    }
    

    /*public void addItemToBuy(ItemToBuy itemToBuy){
        itemsToBuy.add(itemToBuy);
        itemToBuy.setItem(this);
    }

    public void removeItemToBuy(ItemToBuy itemToBuy){
        itemsToBuy.remove(itemToBuy);
        itemToBuy.setItem(null);
    }*/

    public List<ItemToBuy> getItemsToBuy(){
        return itemsToBuy;
    }

    public void setUsers(List<User> favourites){
        this.users=favourites;
    }

    public List<User> getUsers(){
        return users;
    }

    public void setCode(String code){
        this.code=code;
    }
    
    public String getCode(){
        return code;
    }

    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }

    public void setDescription(String description){
        this.description = description;
    }
    
    public String getDescription(){
        return description;
    }

    public void setPrice(Double price){
        this.price = price;
    }
    
    public Double getPrice(){
        return price;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setImageFile(Blob image){
        this.itemImage = image;
    }

    public Blob getImageFile(){
        return itemImage;
    }

    public void setGender(String gender){
        this.gender = gender;
    }
    
    public String getGender(){
        return gender;
    }

    public void setType(String type){
        this.type = type;
    }
    
    public String getType(){
        return type;
    }
}
