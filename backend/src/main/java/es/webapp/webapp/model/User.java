package es.webapp.webapp.model;

import java.sql.Blob;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_user")
public class User {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "rol")
    private String rol;

    private String passwordConfirmation;

    @Lob
    //@Type(type = "org.hibernate.type.ImageType")
    @Column(name = "image")
    private Blob avatar;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonIgnore
    private Direction direction;

    @ManyToMany
    private List<Item> favouritesItems;

    @OneToOne
    private ShoppingCart shoppingCart;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public User(){}

    public Direction getDirection(){
        return direction;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public ShoppingCart getShoppingCart(){
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart){
        this.shoppingCart = shoppingCart;
    }

   /* public void addOrder(Order order){
        orders.add(order);
        order.setUser(this);
    }

    public void removeOrder(Order order){
        orders.remove(order);
        order.setUser(null);
    }*/

    public void setFavouritesItems (List<Item> favourites){
        this.favouritesItems = favourites;
    }

    public List<Item> getFavouritesItems(){
        return favouritesItems;
    }

    /*public void addFavouriteItem(Item favouriteItem){
        favouritesItems.add(favouriteItem);
        favouriteItem.addUser(this);
    }

    public void removeFavouritesItems(Item favouriteItem){
        favouritesItems.remove(favouriteItem);
        favouriteItem.removeUser(null);
    }*/

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
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

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
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
