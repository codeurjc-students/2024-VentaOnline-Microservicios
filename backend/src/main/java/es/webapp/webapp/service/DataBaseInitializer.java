package es.webapp.webapp.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.webapp.webapp.model.Clothes;
import es.webapp.webapp.model.Direction;
import es.webapp.webapp.model.Item;
import es.webapp.webapp.model.ShoppingCart;
import es.webapp.webapp.model.Stock;
import es.webapp.webapp.model.User;
import es.webapp.webapp.model.Clothes.Size;
import es.webapp.webapp.repository.ItemRepo;
import es.webapp.webapp.repository.ClothesRepo;
import es.webapp.webapp.repository.UserRepo;

@Service
public class DataBaseInitializer {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ItemRepo itemRepo;

    @Autowired
    ClothesRepo clothesRepo;
    
    @PostConstruct
    public void init() throws IOException, URISyntaxException {

        //USERS, DIRECTIONS & SHOPPING CARTS
        //1:1 bidirectional relationship

        User user1 = new User();
        user1.setUsername("administrator1");
        user1.setEmail("administrator1@gmail.com");
        user1.setPassword(passwordEncoder.encode("admin123"));
        user1.setPasswordConfirmation(passwordEncoder.encode("admin123"));
        user1.setRol("ADMIN");

        Direction address = new Direction("Calle Roma",2,85503,"Almería");
        user1.setDirection(address);
        Optional<User> user01 = userRepo.findByUsername(user1.getUsername());
        if(!user01.isPresent())
            userRepo.save(user1);

        User user2 = new User();
        user2.setUsername("carlos");
        user2.setName("carlos");
        user2.setEmail("carlos@hotmail.es");
        user2.setPassword(passwordEncoder.encode("user02"));
        user2.setPasswordConfirmation(passwordEncoder.encode("user02"));
        user2.setRol("USER");
        setUserImage(user2, "/images/ava1.jpg");

        Direction address2 = new Direction("Calle de la constitución",1,28933,"Madrid");
        user2.setDirection(address2);
        ShoppingCart shoppingCart = new ShoppingCart();
        user2.setShoppingCart(shoppingCart);
        Optional<User> user02 = userRepo.findByUsername(user2.getUsername());
        if(!user02.isPresent())
            userRepo.save(user2);

        //ITEMS & STOCKS
        //1:N

        //new stock
        Clothes stock1 = new Clothes();
        stock1.setCode("F62A47C");
        Size size1 = Clothes.Size.valueOf("M");
        stock1.setSize(size1);
        stock1.setSotck(20);

        // --> GENERAR Nº ALEATORIO
        System.out.println(UUID.randomUUID().toString().toUpperCase().substring(0, 7));

        //new item
        Item item1 = new Item();
        item1.setCode("VAQMUJ1");
        item1.setName("vaquero regular L30");
        item1.setDescription("Denim·Regular\nHigh rise");
        item1.setPrice(18.00);
        item1.setGender("woman");
        item1.setType("jeans");
        setItemImage(item1, "/images/vaquero_mujer_1.PNG");

        //USERS <--> FAVOURITES ITEMS
        //M:N
        item1.getUsers().add(user2);

        //save item into the db
        Optional<Item> item01 = itemRepo.findByCode(item1.getCode());
        if(!item01.isPresent()){        
            itemRepo.save(item1);
        }
        
        //establish relationship stock <--> item /an item can have several stocks
        stock1.setItem(item1);
        
        //save stock into the db
        Optional<Clothes> stock01 = clothesRepo.findByCode(stock1.getCode());
        if(!stock01.isPresent()){
            clothesRepo.save(stock1);
        }

        //ITEMS TO BUY
        //1:N
        
        //ORDERS
        //1:N

        //USERS <--> FAVOURITES ITEMS
        //M:N


        //save an user into the db

        //an user can have one or more items as favourites

        //save item into the db


    }

    public void setUserImage(User user, String ClasspathResource)throws IOException{
		Resource image = new ClassPathResource(ClasspathResource);
		user.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
	}

    public void setItemImage(Item item, String ClasspathResource)throws IOException{
        Resource image = new ClassPathResource(ClasspathResource);
        item.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
    }
}
