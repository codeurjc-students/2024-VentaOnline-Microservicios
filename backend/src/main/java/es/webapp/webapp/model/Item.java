package es.webapp.webapp.model;

import java.sql.Blob;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_item")   
public class Item {

    private static final Integer NUM = 4; 

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; 

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

    @Column(name = "sizes")
    private String[] sizes = new String[NUM];

    private String size;

    @Column(name = "stocks")
    private Integer[] stocks = new Integer[NUM];

    private Integer stock;

    @ManyToMany
    private List<User> favourites;

    @OneToMany(mappedBy="item")
    private List<ItemToBuy> itemsToBuy;


    public Item(){
        Arrays.fill(stocks, 0);
    }

    public void setSize(String size){
        this.size = size;
    }
    
    public String getSize(){
        return size;
    }

    public void setSizes(String [] size){
        this.sizes = size;
    }
    
    public String [] getSizes(){
        return sizes;
    }

    public void setStock(Integer stock){
        this.stock = stock;
    }
    
    public Integer getStock(){
        return stock;
    }

    public void setStocks(Integer [] stock){
        this.stocks = stock;
    }

    public Integer [] getStocks(){
        return stocks;
    }

    public void addItemToBuy(ItemToBuy itemToBuy){
        itemsToBuy.add(itemToBuy);
        itemToBuy.setItem(this);
    }

    public void removeItemToBuy(ItemToBuy itemToBuy){
        itemsToBuy.remove(itemToBuy);
        itemToBuy.setItem(null);
    }

    public List<User> getFavourites(){
        return favourites;
    }

    public void setFavouritesUsers(List<User> favourites){
        this.favourites = favourites;
    }

    public void addUser(User favouriteUser){
        this.favourites.add(favouriteUser);
    }

    public void removeUser(User favouriteUser){
        this.favourites.remove(favouriteUser);
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
