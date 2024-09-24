package es.webapp.webapp.model;

import java.sql.Blob;
import java.util.List;

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
    private Gender gender;

    @Column(name = "type")
    private Type type;

    @Column(name = "size")
    private String size;

    @Column(name = "stock")
    private Integer stock;

    @ManyToMany
    private List<User> favourites;

    @OneToMany(mappedBy="item")
    private List<ItemToBuy> itemsToBuy;

    public enum Type{
        JEANS, T_SHIRTS, DRESS
    }

    public enum Gender{
        WOMAN, MAN, UNISEX
    }

    public Item(){}

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

    public void setGender(Gender gender){
        this.gender = gender;
    }
    
    public Gender getGender(){
        return gender;
    }

    public void setType(Type type){
        this.type = type;
    }
    
    public Type getType(){
        return type;
    }

    public void setSize(String size){
        this.size = size;
    }
    
    public String getSize(){
        return size;
    }

    public void setStock(Integer stock){
        this.stock = stock;
    }

    public Integer getStock(){
        return stock;
    }
}
