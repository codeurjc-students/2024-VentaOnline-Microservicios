package es.webapp.webapp.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalTime;
import java.util.Optional;

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
import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.Order;
import es.webapp.webapp.model.Order.State;
import es.webapp.webapp.model.Shoe;
import es.webapp.webapp.model.ShoppingCart;
import es.webapp.webapp.model.Size;
import es.webapp.webapp.model.User;
import es.webapp.webapp.repository.ItemRepo;
import es.webapp.webapp.repository.ItemToBuyRepo;
import es.webapp.webapp.repository.OrderRepo;
import es.webapp.webapp.repository.ShoeRepo;
import es.webapp.webapp.repository.ShoppingCartRepo;
import es.webapp.webapp.repository.SizeRepo;
import es.webapp.webapp.repository.ClothesRepo;
import es.webapp.webapp.repository.DirectionRepo;
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

    @Autowired 
    ShoeRepo shoeRepo;
    
    @Autowired
    DirectionRepo directionRepo;

    @Autowired
    ShoppingCartRepo shoppingCartRepo;

    @Autowired
    ItemToBuyRepo itemToBuyRepo;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    SizeRepo sizeRepo;
    
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

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setTotalCost(23.26);
        shoppingCart.setBuyTime(LocalTime.now());
        shoppingCartRepo.save(shoppingCart);

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
        user2.setShoppingCart(shoppingCart);
        Optional<User> user02 = userRepo.findByUsername(user2.getUsername());
        if(!user02.isPresent())
            userRepo.save(user2);


        User user3 = new User();
        user3.setUsername("anonymous");
        user3.setName("anonymous");
        user3.setEmail("an@yahoo.es");
        setUserImage(user3, "/images/user-img.png");

        Optional<User> user03 = userRepo.findByUsername(user3.getUsername());
        if(!user03.isPresent())
            userRepo.save(user3);

        //ITEMS & STOCKS
        //1:N

        //new stock
        Clothes stock1 = new Clothes();
        stock1.setCode("F62A47C");
    
        //Size size1 = Clothes.Size.valueOf("M");
        //stock1.setSize(size1);
        stock1.setStock(20);

        //1:1
        //create a size, 1 stock - 1 sizes
        Size size1 = new Size();
        size1.setCode("SIZE1");
        size1.setLabel("S");

        Optional<Size> size01 = sizeRepo.findByCode("SIZE1");
        if (!size01.isPresent()) {
            sizeRepo.save(size1);
            stock1.setSize(size1);
        }  



        Clothes stock2 = new Clothes();
        stock2.setCode("738D936");
        stock2.setStock(20);

        Clothes stock3 = new Clothes();
        stock3.setCode("8783FC8");
        stock3.setStock(15);   
        
        Clothes stock4 = new Clothes();
        stock4.setCode("B6A0353");;
        stock4.setStock(20);   




        Shoe stock5 = new Shoe(); 
        stock5.setCode("F4ECB71");;
        stock5.setStock(22);

        //1:1
        //create a size, 1 stock - 1 sizes
        Size size3 = new Size();
        size3.setCode("SIZE3");
        size3.setLabel("40");

        Optional<Size> size03 = sizeRepo.findByCode("SIZE3");
        if (!size03.isPresent()) {
            sizeRepo.save(size3);
            stock5.setSize(size3);
        }  




        Shoe stock6 = new Shoe(); 
        stock6.setCode("42FFD15");;
        stock6.setStock(20);

        Shoe stock7 = new Shoe(); 
        stock7.setCode("13A4E8C");;
        stock7.setStock(20);

        Shoe stock8 = new Shoe(); 
        stock8.setCode("54C831B");;
        stock8.setStock(20);

        Shoe stock9 = new Shoe(); 
        stock9.setCode("CEF8EA3");;
        stock9.setStock(20);

        ItemToBuy itemBuy1 = new ItemToBuy();
        itemBuy1.setCode("BFF3680");
        itemBuy1.setSize("S");
        itemBuy1.setCount(2);
        

        // --> GENERAR Nº ALEATORIO, un articulo y su stock tienen cada uno una tira de caracteres aleatorias que se generan automáticamente 
        //System.out.println(UUID.randomUUID().toString().toUpperCase().substring(0, 7));

        //--> new item
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
        //an user can have several favourites items
        item1.getUsers().add(user2);
         

        Optional<Item> item01 = itemRepo.findByCode(item1.getCode());
        if(!item01.isPresent())      
            itemRepo.save(item1);

        stock1.setItem(item1);
        

         /** 1:N 
         * 1º create and save object with OneToMany relationship into the db
         * 2º relate object with ManyToOne relationship to the father
         * 31 save object whit ManyToOne relationship into the db
         */
        //--> establish relationship stock <--> item /an item can have several stocks


        //--> save stock into the db
        Optional<Clothes> stock01 = clothesRepo.findByCode(stock1.getCode());
        if(!stock01.isPresent()){
            clothesRepo.save(stock1);
        }
     

        Item item2 = new Item();
        item2.setCode("VAQMUJ2");
        item2.setName("vaqueros acampanados");
        item2.setDescription("azul cielo");
        item2.setPrice(20.87);
        item2.setGender("woman");
        item2.setType("jeans");
        setItemImage(item2,"/images/vaquero_mujer_2.PNG");

        Optional<Item> item02 = itemRepo.findByCode(item2.getCode());
        if(!item02.isPresent())      
            itemRepo.save(item2);

        //ORDERS
        //1:N

        Order order = new Order();
        order.setCode("1D591DB");
        order.setTotalCost(83.47);
        order.setCreationDate("2024-12-18");
        order.setState(State.PENDING);

        order.setUser(user2);

        
        Optional<Order> order01 = orderRepo.findByCode(order.getCode());
        if(!order01.isPresent()) {
            orderRepo.save(order);
        }

        itemBuy1.setOrder(order);
        //M:N
        //to control the stock of items bought
        itemBuy1.getItems().add(item1);  
        
        
        Optional<ItemToBuy> itemBuy01 = itemToBuyRepo.findByCode(itemBuy1.getCode());
        if(!itemBuy01.isPresent())
            itemToBuyRepo.save(itemBuy1);
        

        Item item3 = new Item();
        item3.setCode("VAQMUJ3");
        item3.setName("vaquero regular L32");
        item3.setDescription("Denim·Regular\nHigh rise");
        item3.setPrice(18.00);
        item3.setGender("woman");
        item3.setType("jeans");
        setItemImage(item3,"/images/vaquero_mujer_3.PNG");

        Optional<Item> item03 = itemRepo.findByCode(item3.getCode());
        if(!item03.isPresent())      
            itemRepo.save(item3);

        Item item4 = new Item();
        item4.setCode("VAQMUJ4");
        item4.setName("jeans ajustados");
        item4.setDescription("de color sólido\nregular");
        item4.setPrice(8.69);
        item4.setGender("woman");
        item4.setType("jeans");
        setItemImage(item4,"/images/vaquero_mujer_4.PNG");

        Optional<Item> item04 = itemRepo.findByCode(item4.getCode());
        if(!item04.isPresent())      
            itemRepo.save(item4);

        Item item5 = new Item();
        item5.setCode("VAQMUJ5");
        item5.setName("jeans de corte muy entallado");
        item5.setDescription("Polyester, azul cielo, Denim·Regular\nHigh rise");
        item5.setPrice(10.00);
        item5.setGender("woman");
        item5.setType("jeans");
        setItemImage(item5,"/images/vaquero_mujer_5.PNG");

        Optional<Item> item05 = itemRepo.findByCode(item5.getCode());
        if(!item05.isPresent())      
            itemRepo.save(item5);

        Item item6 = new Item();
        item6.setCode("VAQMUJ6");
        item6.setName("vaquero regular L32");
        item6.setDescription("Denim·Regular\nHigh rise");
        item6.setPrice(18.00);
        item6.setGender("woman");
        item6.setType("jeans");
        setItemImage(item6,"/images/vaquero_mujer_6.PNG");

        Optional<Item> item06 = itemRepo.findByCode(item6.getCode());
        if(!item06.isPresent())      
            itemRepo.save(item6);

        Item item7 = new Item();
        item7.setCode("VAQMUJ7");
        item7.setName("vaquero recto");
        item7.setDescription("de tiro alto - azul\nDenim·Regular\nHigh rise");
        item7.setPrice(18.00);
        item7.setGender("woman");
        item7.setType("jeans");
        setItemImage(item7,"/images/vaquero_mujer_7.PNG");

        Optional<Item> item07 = itemRepo.findByCode(item7.getCode());
        if(!item07.isPresent())      
            itemRepo.save(item7);

        Item item8 = new Item();
        item8.setCode("VAQMUJ8");
        item8.setName("jeans de corte muy entallado");
        item8.setDescription("Polyester, negro, Denim·Regular\nHigh rise");
        item8.setPrice(10.00);
        item8.setGender("woman");
        item8.setType("jeans");
        setItemImage(item8,"/images/vaquero_mujer_8.PNG");

        Optional<Item> item08 = itemRepo.findByCode(item8.getCode());
        if(!item08.isPresent())      
            itemRepo.save(item8);

        Item item11 = new Item();
        item11.setCode("VAQHOM1");
        item11.setName("slim fit high\nStretch jeans");
        item11.setDescription("Cotton Blendr\nPolyester");
        item11.setPrice(17.53);
        item11.setGender("man");
        item11.setType("jeans");
        setItemImage(item11, "/images/vaquero_hombre_1.PNG");

        Optional<Item> item011 = itemRepo.findByCode(item11.getCode());
        if(!item011.isPresent())      
            itemRepo.save(item11);

        

        Item item12 = new Item();
        item12.setCode("VAQHOM2");
        item12.setName("vaquero skinny L34 - azul");
        item12.setDescription("Polyester,\nDenim·Mid Rise");
        item12.setPrice(18.00);
        item12.setGender("man");
        item12.setType("jeans");
        setItemImage(item12,"/images/vaquero_hombre_2.PNG");

        Optional<Item> item012 = itemRepo.findByCode(item12.getCode());
        if(!item012.isPresent())      
            itemRepo.save(item12);

        

        Item item13 = new Item();
        item13.setCode("VAQHOM3");
        item13.setName("vaquero slim L32 - azul");
        item13.setDescription("Elastane, \nDenim·Mid Rise");
        item13.setPrice(18.00);
        item13.setGender("man");
        item13.setType("jeans");
        setItemImage(item13,"/images/vaquero_hombre_3.PNG");

        Optional<Item> item013 = itemRepo.findByCode(item13.getCode());
        if(!item013.isPresent())      
            itemRepo.save(item13);

        

        Item item14 = new Item();
        item14.setCode("VAQHOM4");
        item14.setName("jeans lavados");
        item14.setDescription("de color slim - azul");
        item14.setPrice(17.46);
        item14.setGender("man");
        item14.setType("jeans");
        setItemImage(item14,"/images/vaquero_hombre_4.PNG");

        Optional<Item> item014 = itemRepo.findByCode(item14.getCode());
        if(!item014.isPresent())      
            itemRepo.save(item14);

        

        Item item15 = new Item();
        item15.setCode("VAQHOM5");
        item15.setName("bonprix vaqueros elásticos");
        item15.setDescription("Denim·Regular, \nLarge, Tall");
        item15.setPrice(12.99);
        item15.setGender("man");
        item15.setType("jeans");
        setItemImage(item15,"/images/vaquero_hombre_5.PNG");

        Optional<Item> item015 = itemRepo.findByCode(item15.getCode());
        if(!item015.isPresent())      
            itemRepo.save(item15);

        

        Item item16 = new Item();
        item16.setCode("VAQHOM6");
        item16.setName("Jack&jones Jeans Intelligence");
        item16.setDescription("Cotton Blend, \nPolyester");
        item16.setPrice(34.99);
        item16.setGender("man");
        item16.setType("jeans");
        setItemImage(item16,"/images/vaquero_hombre_6.PNG");

        Optional<Item> item016 = itemRepo.findByCode(item16.getCode());
        if(!item016.isPresent())      
            itemRepo.save(item16);

        

        Item item17 = new Item();
        item17.setCode("VAQHOM7");
        item17.setName("vaquero regular oscuro");
        item17.setDescription("Denim·Regular");
        item17.setPrice(12.00);
        item17.setGender("man");
        item17.setType("jeans");
        setItemImage(item17,"/images/vaquero_hombre_7.PNG");

        Optional<Item> item017 = itemRepo.findByCode(item17.getCode());
        if(!item017.isPresent())      
            itemRepo.save(item17);

        

        Item item21 = new Item();
        item21.setCode("CAM1");
        item21.setName("camisa formal de primavera y otoño");
        item21.setDescription("camisa de manga larga regular");
        item21.setPrice(17.35);
        item21.setGender("unisex");
        item21.setType("camisa");
        setItemImage(item21, "/images/camisa_1.PNG");

        Optional<Item> item021 = itemRepo.findByCode(item21.getCode());
        if(!item021.isPresent())      
            itemRepo.save(item21);

        

        Item item22 = new Item();
        item22.setCode("CAM2");
        item22.setName("camisa de manga larga con botones");
        item22.setDescription("");
        item22.setPrice(7.57);
        item22.setGender("unisex");
        item22.setType("camisa");
        setItemImage(item22,"/images/camisa_2.PNG");

        Optional<Item> item022 = itemRepo.findByCode(item22.getCode());
        if(!item022.isPresent())      
            itemRepo.save(item22);

        

        Item item23 = new Item();
        item23.setCode("CAM3");
        item23.setName("camisa de manga larga");
        item23.setDescription("mezcla de algodón");
        item23.setPrice(32.27);
        item23.setGender("unisex");
        item23.setType("camisa");
        setItemImage(item23,"/images/camisa_3.PNG");

        Optional<Item> item023 = itemRepo.findByCode(item23.getCode());
        if(!item023.isPresent())      
            itemRepo.save(item23);

        

        Item item24 = new Item();
        item24.setCode("CAM4");
        item24.setName("camisa casual");
        item24.setDescription("con estampado y bolsillo");
        item24.setPrice(8.99);
        item24.setGender("unisex");
        item24.setType("camisa");
        setItemImage(item24,"/images/camisa_4.PNG");

        Optional<Item> item024 = itemRepo.findByCode(item24.getCode());
        if(!item024.isPresent())      
            itemRepo.save(item24);

        

        Item item25 = new Item();
        item25.setCode("CAM5");
        item25.setName("camisa de franela, \nde manga larga");
        item25.setDescription("con clásico patrón");
        item25.setPrice(9.60);
        item25.setGender("unisex");
        item25.setType("camisa");
        setItemImage(item25,"/images/camisa_5.PNG");

        Optional<Item> item025 = itemRepo.findByCode(item25.getCode());
        if(!item025.isPresent())      
            itemRepo.save(item25);

        

        Item item26 = new Item();
        item26.setCode("CAM6");
        item26.setName("camisa elegante con estampado");
        item26.setDescription("Camisa de manga larga");
        item26.setPrice(18.63);
        item26.setGender("unisex");
        item26.setType("camisa");
        setItemImage(item26,"/images/camisa_6.PNG");

        Optional<Item> item026 = itemRepo.findByCode(item26.getCode());
        if(!item026.isPresent())      
            itemRepo.save(item26);

        

        Item item27 = new Item();
        item27.setCode("CAM7");
        item27.setName("camisa informal");
        item27.setDescription("de algodón puro, Oxford, \ncamisa de vestir, liso");
        item27.setPrice(5.69);
        item27.setGender("unisex");
        item27.setType("camisa");
        setItemImage(item27,"/images/camisa_7.PNG");   
        
        Optional<Item> item027 = itemRepo.findByCode(item27.getCode());
        if(!item027.isPresent())      
            itemRepo.save(item27);

        
        
        Item item31 = new Item();
        item31.setCode("CAM8");
        item31.setName("camiseta de corte regular");
        item31.setDescription("en algodón");
        item31.setPrice(420.00);
        item31.setGender("unisex");
        item31.setType("camiseta");
        setItemImage(item31, "/images/camiseta_2.PNG");

        Optional<Item> item031 = itemRepo.findByCode(item31.getCode());
        if(!item031.isPresent())      
            itemRepo.save(item31);

        

        Item item32 = new Item();
        item32.setCode("CAM9");
        item32.setName("camiseta personalizada");
        item32.setDescription("manga corta");
        item32.setPrice(8.90);
        item32.setGender("unisex");
        item32.setType("camiseta");
        setItemImage(item32,"/images/camiseta_3.PNG");

        Optional<Item> item032 = itemRepo.findByCode(item32.getCode());
        if(!item032.isPresent())      
            itemRepo.save(item32);

        

        Item item33 = new Item();
        item33.setCode("CAM10");
        item33.setName("camiseta de manga corta");
        item33.setDescription("FAMILY ADAN");
        item33.setPrice(20.00);
        item33.setGender("unisex");
        item33.setType("camiseta");
        setItemImage(item33,"/images/camiseta_5.PNG");

        Optional<Item> item033 = itemRepo.findByCode(item33.getCode());
        if(!item033.isPresent())      
            itemRepo.save(item33);

        

        Item item34 = new Item();
        item34.setCode("CAM11");
        item34.setName("camiseta personalizada");
        item34.setDescription("camiseta con foto");
        item34.setPrice(17.00);
        item34.setGender("unisex");
        item34.setType("camiseta");
        setItemImage(item34,"/images/camiseta_6.PNG");

        //item34.getUsers().add(user2);

        Optional<Item> item034 = itemRepo.findByCode(item34.getCode());
        if(!item034.isPresent())      
            itemRepo.save(item34);

        

        Item item35 = new Item();
        item35.setCode("CAM12");
        item35.setName("camiseta adulto");
        item35.setDescription("Color Hecom");
        item35.setPrice(0.71);
        item35.setGender("unisex");
        item35.setType("camiseta");
        setItemImage(item35,"/images/camiseta_7.PNG");

        Optional<Item> item035 = itemRepo.findByCode(item35.getCode());
        if(!item035.isPresent())      
            itemRepo.save(item35);

        

        Item item36 = new Item();
        item36.setCode("CAM13");
        item36.setName("camiseta básica barata");
        item36.setDescription("con cuello redondo");
        item36.setPrice(2.30);
        item36.setGender("unisex");
        item36.setType("camiseta");
        setItemImage(item36,"/images/camisetas_1.PNG");

        Optional<Item> item036 = itemRepo.findByCode(item36.getCode());
        if(!item036.isPresent())      
            itemRepo.save(item36);

        

        Item item37 = new Item();
        item37.setCode("CAM14");
        item37.setName("camiseta regular fit");
        item37.setDescription("camiseta manga corta");
        item37.setPrice(6.99);
        item37.setGender("unisex");
        item37.setType("camiseta");
        setItemImage(item37,"/images/camisetas_4.PNG");   

        Optional<Item> item037 = itemRepo.findByCode(item37.getCode());
        if(!item037.isPresent())      
            itemRepo.save(item37);

        

        Item item41 = new Item();
        item41.setCode("ZAP1");
        item41.setName("botas chukka \ncasuales para hombre");
        item41.setDescription("botas de invierno");
        item41.setPrice(23.26);
        item41.setGender("unisex");
        item41.setType("zapato");
        setItemImage(item41, "/images/zapato_1.PNG");

        Optional<Item> item041 = itemRepo.findByCode(item41.getCode());
        if(!item041.isPresent())      
            itemRepo.save(item41);

        stock5.setItem(item41);
        
         /** 1:N 
         * 1º create and save object with OneToMany relationship into the db
         * 2º relate object with ManyToOne relationship to the father
         * 31 save object whit ManyToOne relationship into the db
         */
        //--> establish relationship stock <--> item /an item can have several stocks


        //--> save stock into the db
        Optional<Shoe> stock05 = shoeRepo.findByCode(stock5.getCode());
        if(!stock05.isPresent()){
            shoeRepo.save(stock5);
        }

        ItemToBuy itemBuy2 = new ItemToBuy();
        itemBuy2.setCode("GFD7643");
        itemBuy2.setSize("40");
        itemBuy2.setCount(1);

    
            
        //ITEMS TO BUY
        //1:N
        itemBuy2.setShoppingCart(user2.getShoppingCart());
        //var cost = shoppingCart.getTotalCost();
        //cost += (itemBuy2.getItems().get(0).getPrice()*itemBuy2.getCount());
        //shoppingCart.setTotalCost(cost);
        itemBuy2.getItems().add(item41); 

        Optional<ItemToBuy> itemBuy02 = itemToBuyRepo.findByCode(itemBuy2.getCode());
        if(!itemBuy02.isPresent())
            itemToBuyRepo.save(itemBuy2);




        Item item42 = new Item();
        item42.setCode("ZAP2");
        item42.setName("zapatos deportivos");
        item42.setDescription("sólidos de moda, \ncasuales con cordones, resistentes");
        item42.setPrice(14.46);
        item42.setGender("unisex");
        item42.setType("zapato");
        setItemImage(item42,"/images/zapato_2.PNG");

        Optional<Item> item042 = itemRepo.findByCode(item42.getCode());
        if(!item042.isPresent())      
            itemRepo.save(item42);


        Item item43 = new Item();
        item43.setCode("ZAP3");
        item43.setName("zapatos casuales");
        item43.setDescription("con cordones de PU, \nsuperior vintage");
        item43.setPrice(12.89);
        item43.setGender("unisex");
        item43.setType("zapato");
        setItemImage(item43,"/images/zapato_3.PNG");

        Optional<Item> item043 = itemRepo.findByCode(item43.getCode());
        if(!item043.isPresent())      
            itemRepo.save(item43);


        Item item44 = new Item();
        item44.setCode("ZAP4");
        item44.setName("zapatos casuales cómodos");
        item44.setDescription("de lona, \ncon diseño de color sólido");
        item44.setPrice(16.09);
        item44.setGender("unisex");
        item44.setType("zapato");
        setItemImage(item44,"/images/zapato_4.PNG");

        Optional<Item> item044 = itemRepo.findByCode(item44.getCode());
        if(!item044.isPresent())      
            itemRepo.save(item44);


        Item item45 = new Item();
        item45.setCode("ZAP5");
        item45.setName("zapatos bajos");
        item45.setDescription("con cordones de bloque de color tejido, \npara acttividades al aire libre");
        item45.setPrice(14.89);
        item45.setGender("unisex");
        item45.setType("zapato");
        setItemImage(item45,"/images/zapato_5.PNG");

        Optional<Item> item045 = itemRepo.findByCode(item45.getCode());
        if(!item045.isPresent())      
            itemRepo.save(item45);


        Item item46 = new Item();
        item46.setCode("ZAP6");
        item46.setName("sneakers casuales");
        item46.setDescription("con costuras hechas a mano,\nzapatos planos\n para caminar al aire libre");
        item46.setPrice(17.79);
        item46.setGender("unisex");
        item46.setType("zapato");
        setItemImage(item46,"/images/zapato_6.PNG");

        Optional<Item> item046 = itemRepo.findByCode(item46.getCode());
        if(!item046.isPresent())      
            itemRepo.save(item46);


        Item item47 = new Item();
        item47.setCode("ZAP7");
        item47.setName("zapatilla Barefoot");
        item47.setDescription("zapato minimalista\n calzado gym trail running exterior");
        item47.setPrice(52.99);
        item47.setGender("unisex");
        item47.setType("zapato");
        setItemImage(item47,"/images/zapato_7.PNG");   
        
        Optional<Item> item047 = itemRepo.findByCode(item47.getCode());
        if(!item047.isPresent())      
            itemRepo.save(item47);

        
        Item item48 = new Item();
        item48.setCode("ZAP8");
        item48.setName("zapatillas Piper");
        item48.setDescription("plano, cordones, casual");
        item48.setPrice(35.99);
        item48.setGender("unisex");
        item48.setType("zapato");
        setItemImage(item48, "/images/zapato_8.PNG");

        Optional<Item> item048 = itemRepo.findByCode(item48.getCode());
        if(!item048.isPresent())      
            itemRepo.save(item48);


        Item item49 = new Item();
        item49.setCode("ZAP9");
        item49.setName("zapatillas de plataforma cómodas");
        item49.setDescription("ligeras, transìrables y antideslizantes");
        item49.setPrice(13.59);
        item49.setGender("unisex");
        item49.setType("zapato");
        setItemImage(item49,"/images/zapato_9.PNG");

        Optional<Item> item049 = itemRepo.findByCode(item49.getCode());
        if(!item049.isPresent())      
            itemRepo.save(item49);


        Item item50 = new Item();
        item50.setCode("ZAP10");
        item50.setName("zapatilla de skate de lona");
        item50.setDescription("con buena tracción, \naltas y con cordones");
        item50.setPrice(17.56);
        item50.setGender("unisex");
        item50.setType("zapato");
        setItemImage(item50,"/images/zapato_10.PNG");

        Optional<Item> item050 = itemRepo.findByCode(item50.getCode());
        if(!item050.isPresent())      
            itemRepo.save(item50);


        Item item51 = new Item();
        item51.setCode("ZAP11");
        item51.setName("zapatos códos y casuales");
        item51.setDescription("zapatos cómodos con suela suave bordada");
        item51.setPrice(11.86);
        item51.setGender("unisex");
        item51.setType("zapato");
        setItemImage(item51,"/images/zapato_11.PNG");

        Optional<Item> item051 = itemRepo.findByCode(item51.getCode());
        if(!item051.isPresent())      
            itemRepo.save(item51);


        Item item52 = new Item();
        item52.setCode("ZAP12");
        item52.setName("zapatos de skate");
        item52.setDescription("de caña baja vintage de color liso");
        item52.setPrice(11.02);
        item52.setGender("unisex");
        item52.setType("zapato");
        setItemImage(item52,"/images/zapato_12.PNG");

        Optional<Item> item052 = itemRepo.findByCode(item52.getCode());
        if(!item052.isPresent())      
            itemRepo.save(item52);


        Item item53 = new Item();
        item53.setCode("ZAP13");
        item53.setName("Pier One unisex");
        item53.setDescription("Dark blue");
        item53.setPrice(27.99);
        item53.setGender("unisex");
        item53.setType("zapato");
        setItemImage(item53,"/images/zapato_13.PNG");


        Optional<Item> item053 = itemRepo.findByCode(item53.getCode());
        if(!item053.isPresent())      
            itemRepo.save(item53);


     


        //--> save item into the db
        //Optional<Item> item01 = itemRepo.findByCode(item1.getCode());
        //if(!item01.isPresent())      
        //    itemRepo.save(item1);



        
        //1:1
        //create a size, 1 stock - 1 sizes
        Size size2 = new Size();
        size2.setCode("SIZE2");
        size2.setLabel("M");
        stock2.setSize(size2);  
        Optional<Size> size02 = sizeRepo.findByCode(size2.getCode());
        if(!size02.isPresent()){
            sizeRepo.save(size2);           
        }

        Size size4 = new Size();
        size4.setCode("SIZE4");
        size4.setLabel("36");
        stock3.setSize(size4);
        Optional<Size> size04 = sizeRepo.findByCode(size4.getCode());
        if(!size04.isPresent())
            sizeRepo.save(size4);
        
        stock2.setItem(item34);
        
        stock3.setItem(item34);

        Optional<Clothes> stock02 = clothesRepo.findByCode(stock2.getCode());
        if(!stock02.isPresent())
            clothesRepo.save(stock2);

        Optional<Clothes> stock03 = clothesRepo.findByCode(stock3.getCode());
        if(!stock03.isPresent()){
            clothesRepo.save(stock3);
        }



        Optional<Clothes> stock04 = clothesRepo.findByCode(stock4.getCode());
        if(!stock04.isPresent())
            clothesRepo.save(stock4);

        Optional<Shoe> stock06 = shoeRepo.findByCode(stock6.getCode());
        if(!stock06.isPresent()){
            shoeRepo.save(stock6);
        }

        Optional<Shoe> stock07 = shoeRepo.findByCode(stock7.getCode());
        if(!stock07.isPresent()){
            shoeRepo.save(stock7);
        }

        Optional<Shoe> stock08 = shoeRepo.findByCode(stock8.getCode());
        if(!stock08.isPresent()){
            shoeRepo.save(stock8);
        }

        Optional<Shoe> stock09 = shoeRepo.findByCode(stock9.getCode());
        if(!stock09.isPresent()){
            shoeRepo.save(stock9);
        }

        

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
