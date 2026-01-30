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

    public DataBaseInitializer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        
    }
    
    @PostConstruct
    public void init() throws IOException, URISyntaxException {
        
        if (userRepo.count() > 0) {
            System.out.println("📦 Ya hay usuarios en la BD, no se inicializa");
            return;
        }

        System.out.println("🚀 Inicializando base de datos...");

    //USERS, DIRECTIONS & SHOPPING CARTS
        //1:1 bidirectional relationship

    // --- Direcciones ---
        Direction address1 = directionRepo.findByCode("address01")
                .orElseGet(() -> {
                    Direction d = new Direction("Calle Roma", 2, 85503, "Almería");
                    d.setCode("address01");
                    directionRepo.save(d);
                    return d;
                });

        Direction address2 = directionRepo.findByCode("address02")
                .orElseGet(() -> {
                    Direction d = new Direction("Calle de la constitución", 1, 28933, "Madrid");
                    d.setCode("address02");
                    return directionRepo.save(d);
                });

        // --- ShoppingCarts ---
        ShoppingCart cart1 = shoppingCartRepo.findByCode("sh_cart1")
                .orElseGet(() -> {
                    ShoppingCart sc = new ShoppingCart();
                    sc.setCode("sh_cart1");
                    sc.setTotalCost(23.26);
                    sc.setBuyTime(LocalTime.now().toString());
                    return shoppingCartRepo.save(sc);
                });

        // --- Usuarios ---
        User user1 = userRepo.findByUsername("administrator1")
                .orElseGet(() -> {
                    User u = new User();
                    u.setUsername("administrator1");
                    u.setEmail("administrator1@gmail.com");
                    u.setPassword(passwordEncoder.encode("admin123"));
                    u.setPasswordConfirmation(passwordEncoder.encode("admin123"));
                    u.setRol("ADMIN");
                    u.setDirection(address1);
                    return userRepo.save(u);
                });

        User user2 = userRepo.findByUsername("carlos")
                .orElseGet(() -> {
                    User u = new User();
                    u.setUsername("carlos");
                    u.setEmail("carlos@hotmail.es");
                    u.setPassword(passwordEncoder.encode("user02"));
                    u.setPasswordConfirmation(passwordEncoder.encode("user02"));
                    u.setRol("USER");
                    u.setDirection(address2);
                    u.setShoppingCart(cart1);
                    return userRepo.save(u);
                });
        
        
        User user3 = userRepo.findByUsername("anonymous")
        .orElseGet(() -> {
            User u = new User();
            u.setUsername("anonymous");
            u.setName("anonymous");
            u.setEmail("an@yahoo.es");
            try {
                setUserImage(u, "/images/user-img.png");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return userRepo.save(u);
        });


        //ITEMS & STOCKS

        Size size1 = sizeRepo.findByCode("SIZE1")
        .orElseGet(() -> {
            Size s = new Size();
            s.setCode("SIZE1");
            s.setLabel("S");
            return sizeRepo.save(s);
        });

        Clothes stock1 = clothesRepo.findByCode("F62A47C")
        .orElseGet(() -> {
            Clothes c = new Clothes();
            c.setCode("F62A47C");
            c.setStock(20);
            c.setSize(size1);
            return clothesRepo.save(c);
        });

        Clothes stock2 = clothesRepo.findByCode("738D936")
        .orElseGet(() -> {
            Clothes c = new Clothes();
            c.setCode("738D936");
            c.setStock(20);
            return clothesRepo.save(c);
        });

        Clothes stock3 = clothesRepo.findByCode("8783FC8")
                .orElseGet(() -> {
                    Clothes c = new Clothes();
                    c.setCode("8783FC8");
                    c.setStock(15);
                    return clothesRepo.save(c);
                });

        Clothes stock4 = clothesRepo.findByCode("B6A0353")
                .orElseGet(() -> {
                    Clothes c = new Clothes();
                    c.setCode("B6A0353");
                    c.setStock(20);
                    return clothesRepo.save(c);
                });


        Size size3 = sizeRepo.findByCode("SIZE3")
        .orElseGet(() -> {
            Size s = new Size();
            s.setCode("SIZE3");
            s.setLabel("40");
            return sizeRepo.save(s);
        });

        Shoe stock5 = shoeRepo.findByCode("F4ECB71")
        .orElseGet(() -> {
            Shoe s = new Shoe();
            s.setCode("F4ECB71");
            s.setStock(22);
            s.setSize(size3);
            return shoeRepo.save(s);
        });

        Shoe stock6 = shoeRepo.findByCode("42FFD15")
                .orElseGet(() -> {
                    Shoe s = new Shoe();
                    s.setCode("42FFD15");
                    s.setStock(20);
                    return shoeRepo.save(s);
                });

        Shoe stock7 = shoeRepo.findByCode("13A4E8C")
                .orElseGet(() -> {
                    Shoe s = new Shoe();
                    s.setCode("13A4E8C");
                    s.setStock(20);
                    return shoeRepo.save(s);
                });

        Shoe stock8 = shoeRepo.findByCode("54C831B")
                .orElseGet(() -> {
                    Shoe s = new Shoe();
                    s.setCode("54C831B");
                    s.setStock(20);
                    return shoeRepo.save(s);
                });

        Shoe stock9 = shoeRepo.findByCode("CEF8EA3")
                .orElseGet(() -> {
                    Shoe s = new Shoe();
                    s.setCode("CEF8EA3");
                    s.setStock(20);
                    return shoeRepo.save(s);
                });


        ItemToBuy itemBuy1 = itemToBuyRepo.findByCode("BFF3680")
            .orElseGet(() -> {
                ItemToBuy it = new ItemToBuy();
                it.setCode("BFF3680");
                it.setSize("S");
                it.setCount(2);
                return itemToBuyRepo.save(it);
            });

        

        // --> GENERAR Nº ALEATORIO, un articulo y su stock tienen cada uno una tira de caracteres aleatorias que se generan automáticamente 
        //System.out.println(UUID.randomUUID().toString().toUpperCase().substring(0, 7));

        //--> new item
        Item item1 = itemRepo.findByCode("VAQMUJ1")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("VAQMUJ1");
            i.setName("vaquero regular L30");
            i.setDescription("Denim·Regular\nHigh rise");
            i.setPrice(18.00);
            i.setGender("woman");
            i.setType("jeans");
            try {
                setItemImage(i, "/images/vaquero_mujer_1.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            i.getUsers().add(user2); // favoritos
            return itemRepo.save(i);
        });

        stock1.setItem(item1);
        clothesRepo.save(stock1);


        //clothesRepo.save(stock1);

         /** 1:N 
         * 1º create and save object with OneToMany relationship into the db
         * 2º relate object with ManyToOne relationship to the father
         * 31 save object whit ManyToOne relationship into the db
         */
        //--> establish relationship stock <--> item /an item can have several stocks


        //--> save stock into the db
        //Optional<Clothes> stock01 = clothesRepo.findByCode(stock1.getCode());


        //if(!stock01.isPresent())
            
        Item item2 = itemRepo.findByCode("VAQMUJ2")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("VAQMUJ2");
            i.setName("vaqueros acampanados");
            i.setDescription("azul cielo");
            i.setPrice(20.87);
            i.setGender("woman");
            i.setType("jeans");
            try {
                setItemImage(i, "/images/vaquero_mujer_2.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        //ORDERS
        //1:N

        Order order = orderRepo.findByCode("1D591DB")
        .orElseGet(() -> {
            Order o = new Order();
            o.setCode("1D591DB");
            o.setTotalCost(83.47);
            o.setCreationDate("2024-12-18");
            o.setState(State.PENDING.name());
            o.setUser(user2);
            return orderRepo.save(o);
        });

        itemBuy1.setOrder(order);
        itemBuy1.getItems().add(item1);
        itemToBuyRepo.save(itemBuy1);


        Item item3 = itemRepo.findByCode("VAQMUJ3")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("VAQMUJ3");
            i.setName("vaquero regular L32");
            i.setDescription("Denim·Regular\nHigh rise");
            i.setPrice(18.00);
            i.setGender("woman");
            i.setType("jeans");
            try {
                setItemImage(i, "/images/vaquero_mujer_3.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        Item item4 = itemRepo.findByCode("VAQMUJ4")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("VAQMUJ4");
            i.setName("jeans ajustados");
            i.setDescription("de color sólido\nregular");
            i.setPrice(8.69);
            i.setGender("woman");
            i.setType("jeans");
            try {
                setItemImage(i, "/images/vaquero_mujer_4.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        Item item5 = itemRepo.findByCode("VAQMUJ5")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("VAQMUJ5");
            i.setName("jeans de corte muy entallado");
            i.setDescription("Polyester, azul cielo, Denim·Regular\nHigh rise");
            i.setPrice(10.00);
            i.setGender("woman");
            i.setType("jeans");
            try {
                setItemImage(i, "/images/vaquero_mujer_5.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        Item item6 = itemRepo.findByCode("VAQMUJ6")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("VAQMUJ6");
            i.setName("vaquero regular L32");
            i.setDescription("Denim·Regular\nHigh rise");
            i.setPrice(18.00);
            i.setGender("woman");
            i.setType("jeans");
            try {
                setItemImage(i, "/images/vaquero_mujer_6.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        Item item7 = itemRepo.findByCode("VAQMUJ7")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("VAQMUJ7");
            i.setName("vaquero recto");
            i.setDescription("de tiro alto - azul\nDenim·Regular\nHigh rise");
            i.setPrice(18.00);
            i.setGender("woman");
            i.setType("jeans");
            try {
                setItemImage(i, "/images/vaquero_mujer_7.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        Item item8 = itemRepo.findByCode("VAQMUJ8")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("VAQMUJ8");
            i.setName("jeans de corte muy entallado");
            i.setDescription("Polyester, negro, Denim·Regular\nHigh rise");
            i.setPrice(10.00);
            i.setGender("woman");
            i.setType("jeans");
            try {
                setItemImage(i, "/images/vaquero_mujer_8.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        Item item11 = itemRepo.findByCode("VAQHOM1")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("VAQHOM1");
            i.setName("slim fit high\nStretch jeans");
            i.setDescription("Cotton Blendr\nPolyester");
            i.setPrice(17.53);
            i.setGender("man");
            i.setType("jeans");
            try {
                setItemImage(i, "/images/vaquero_hombre_1.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });
 

        Item item12 = itemRepo.findByCode("VAQHOM2")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("VAQHOM2");
            i.setName("vaquero skinny L34 - azul");
            i.setDescription("Polyester,\nDenim·Mid Rise");
            i.setPrice(18.00);
            i.setGender("man");
            i.setType("jeans");
            try {
                setItemImage(i, "/images/vaquero_hombre_2.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        Item item13 = itemRepo.findByCode("VAQHOM3")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("VAQHOM3");
            i.setName("vaquero slim L32 - azul");
            i.setDescription("Elastane,\nDenim·Mid Rise");
            i.setPrice(18.00);
            i.setGender("man");
            i.setType("jeans");
            try {
                setItemImage(i, "/images/vaquero_hombre_3.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        Item item14 = itemRepo.findByCode("VAQHOM4")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("VAQHOM4");
            i.setName("jeans lavados");
            i.setDescription("de color slim - azul");
            i.setPrice(17.46);
            i.setGender("man");
            i.setType("jeans");
            try {
                setItemImage(i, "/images/vaquero_hombre_4.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        

       Item item15 = itemRepo.findByCode("VAQHOM5")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("VAQHOM5");
            i.setName("bonprix vaqueros elásticos");
            i.setDescription("Denim·Regular,\nLarge, Tall");
            i.setPrice(12.99);
            i.setGender("man");
            i.setType("jeans");
            try {
                setItemImage(i, "/images/vaquero_hombre_5.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        

        Item item16 = itemRepo.findByCode("VAQHOM6")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("VAQHOM6");
            i.setName("Jack&jones Jeans Intelligence");
            i.setDescription("Cotton Blend,\nPolyester");
            i.setPrice(34.99);
            i.setGender("man");
            i.setType("jeans");
            try {
                setItemImage(i, "/images/vaquero_hombre_6.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        

        Item item17 = itemRepo.findByCode("VAQHOM7")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("VAQHOM7");
            i.setName("vaquero regular oscuro");
            i.setDescription("Denim·Regular");
            i.setPrice(12.00);
            i.setGender("man");
            i.setType("jeans");
            try {
                setItemImage(i, "/images/vaquero_hombre_7.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        

        Item item21 = itemRepo.findByCode("CAM1")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("CAM1");
            i.setName("camisa formal de primavera y otoño");
            i.setDescription("camisa de manga larga regular");
            i.setPrice(17.35);
            i.setGender("unisex");
            i.setType("camisa");
            try {
                setItemImage(i, "/images/camisa_1.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        

        Item item22 = itemRepo.findByCode("CAM2")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("CAM2");
            i.setName("camisa de manga larga con botones");
            i.setDescription("");
            i.setPrice(7.57);
            i.setGender("unisex");
            i.setType("camisa");
            try {
                setItemImage(i, "/images/camisa_2.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });

        

        Item item23 = itemRepo.findByCode("CAM3")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("CAM3");
            i.setName("camisa de manga larga");
            i.setDescription("mezcla de algodón");
            i.setPrice(32.27);
            i.setGender("unisex");
            i.setType("camisa");
            try {
                setItemImage(i, "/images/camisa_3.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        

        Item item24 = itemRepo.findByCode("CAM4")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("CAM4");
            i.setName("camisa casual");
            i.setDescription("con estampado y bolsillo");
            i.setPrice(8.99);
            i.setGender("unisex");
            i.setType("camisa");
            try {
                setItemImage(i, "/images/camisa_4.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        

        Item item25 = itemRepo.findByCode("CAM5")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("CAM5");
            i.setName("camisa de franela,\nde manga larga");
            i.setDescription("con clásico patrón");
            i.setPrice(9.60);
            i.setGender("unisex");
            i.setType("camisa");
            try {
                setItemImage(i, "/images/camisa_5.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        

        Item item26 = itemRepo.findByCode("CAM6")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("CAM6");
            i.setName("camisa elegante con estampado");
            i.setDescription("Camisa de manga larga");
            i.setPrice(18.63);
            i.setGender("unisex");
            i.setType("camisa");
            try {
                setItemImage(i, "/images/camisa_6.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        

        Item item27 = itemRepo.findByCode("CAM7")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("CAM7");
            i.setName("camisa informal");
            i.setDescription("de algodón puro, Oxford,\ncamisa de vestir, liso");
            i.setPrice(5.69);
            i.setGender("unisex");
            i.setType("camisa");
            try {
                setItemImage(i, "/images/camisa_7.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        
        
        Item item31 = itemRepo.findByCode("CAM8")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("CAM8");
            i.setName("camiseta de corte regular");
            i.setDescription("en algodón");
            i.setPrice(420.00);
            i.setGender("unisex");
            i.setType("camiseta");
            try {
                setItemImage(i, "/images/camiseta_2.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        

        Item item32 = itemRepo.findByCode("CAM9")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("CAM9");
            i.setName("camiseta personalizada");
            i.setDescription("manga corta");
            i.setPrice(8.90);
            i.setGender("unisex");
            i.setType("camiseta");
            try {
                setItemImage(i, "/images/camiseta_3.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        

        Item item33 = itemRepo.findByCode("CAM10")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("CAM10");
            i.setName("camiseta de manga corta");
            i.setDescription("FAMILY ADAN");
            i.setPrice(20.00);
            i.setGender("unisex");
            i.setType("camiseta");
            try {
                setItemImage(i, "/images/camiseta_5.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        

        Item item34 = itemRepo.findByCode("CAM11")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("CAM11");
            i.setName("camiseta personalizada");
            i.setDescription("camiseta con foto");
            i.setPrice(17.00);
            i.setGender("unisex");
            i.setType("camiseta");
            try {
                setItemImage(i, "/images/camiseta_6.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });

        // relaciones
        stock2.setItem(item34);
        stock3.setItem(item34);


        Item item35 = itemRepo.findByCode("CAM12")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("CAM12");
            i.setName("camiseta adulto");
            i.setDescription("Color Hecom");
            i.setPrice(0.71);
            i.setGender("unisex");
            i.setType("camiseta");
            try {
                setItemImage(i, "/images/camiseta_7.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        
Item item36 = itemRepo.findByCode("CAM13")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("CAM13");
            i.setName("camiseta básica barata");
            i.setDescription("con cuello redondo");
            i.setPrice(2.30);
            i.setGender("unisex");
            i.setType("camiseta");
            try {
                setItemImage(i, "/images/camisetas_1.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        

        Item item37 = itemRepo.findByCode("CAM14")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("CAM14");
            i.setName("camiseta regular fit");
            i.setDescription("camiseta manga corta");
            i.setPrice(6.99);
            i.setGender("unisex");
            i.setType("camiseta");
            try {
                setItemImage(i, "/images/camisetas_4.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        

        Item item41 = itemRepo.findByCode("ZAP1")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("ZAP1");
            i.setName("botas chukka \ncasuales para hombre");
            i.setDescription("botas de invierno");
            i.setPrice(23.26);
            i.setGender("unisex");
            i.setType("zapato");
            try {
                setItemImage(i, "/images/zapato_1.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });

        stock5.setItem(item41);

        
        
         /** 1:N 
         * 1º create and save object with OneToMany relationship into the db
         * 2º relate object with ManyToOne relationship to the father
         * 31 save object whit ManyToOne relationship into the db
         */
        //--> establish relationship stock <--> item /an item can have several stocks


        //--> save stock into the db
        //Optional<Shoe> stock05 = shoeRepo.findByCode(stock5.getCode());


        //if(!stock05.isPresent())
            //shoeRepo.save(stock5);
        

       ItemToBuy itemBuy2 = itemToBuyRepo.findByCode("GFD7643")
        .orElseGet(() -> {
            ItemToBuy itb = new ItemToBuy();
            itb.setCode("GFD7643");
            itb.setSize("40");
            itb.setCount(1);
            itb.setShoppingCart(user2.getShoppingCart());
            itb.getItems().add(item41);
            return itemToBuyRepo.save(itb);
        });




        Item item42 = itemRepo.findByCode("ZAP2")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("ZAP2");
            i.setName("zapatos deportivos");
            i.setDescription("sólidos de moda, \ncasuales con cordones, resistentes");
            i.setPrice(14.46);
            i.setGender("unisex");
            i.setType("zapato");
            try {
                setItemImage(i, "/images/zapato_2.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        Item item43 = itemRepo.findByCode("ZAP3")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("ZAP3");
            i.setName("zapatos casuales");
            i.setDescription("con cordones de PU, \nsuperior vintage");
            i.setPrice(12.89);
            i.setGender("unisex");
            i.setType("zapato");
            try {
                setItemImage(i, "/images/zapato_3.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });



        Item item44 = itemRepo.findByCode("ZAP4")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("ZAP4");
            i.setName("zapatos casuales cómodos");
            i.setDescription("de lona, \ncon diseño de color sólido");
            i.setPrice(16.09);
            i.setGender("unisex");
            i.setType("zapato");
            try {
                setItemImage(i, "/images/zapato_4.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });



        Item item45 = itemRepo.findByCode("ZAP5")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("ZAP5");
            i.setName("zapatos bajos");
            i.setDescription("con cordones de bloque de color tejido, \npara actividades al aire libre");
            i.setPrice(14.89);
            i.setGender("unisex");
            i.setType("zapato");
            try {
                setItemImage(i, "/images/zapato_5.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });



        Item item46 = itemRepo.findByCode("ZAP6")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("ZAP6");
            i.setName("sneakers casuales");
            i.setDescription("con costuras hechas a mano,\nzapatos planos\npara caminar al aire libre");
            i.setPrice(17.79);
            i.setGender("unisex");
            i.setType("zapato");
            try {
                setItemImage(i, "/images/zapato_6.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });



        Item item47 = itemRepo.findByCode("ZAP7")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("ZAP7");
            i.setName("zapatilla Barefoot");
            i.setDescription("zapato minimalista\ncalzado gym trail running exterior");
            i.setPrice(52.99);
            i.setGender("unisex");
            i.setType("zapato");
            try {
                setItemImage(i, "/images/zapato_7.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });


        
        Item item48 = itemRepo.findByCode("ZAP8")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("ZAP8");
            i.setName("zapatillas Piper");
            i.setDescription("plano, cordones, casual");
            i.setPrice(35.99);
            i.setGender("unisex");
            i.setType("zapato");
            try {
                setItemImage(i, "/images/zapato_8.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });



        Item item49 = itemRepo.findByCode("ZAP9")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("ZAP9");
            i.setName("zapatillas de plataforma cómodas");
            i.setDescription("ligeras, transpirables y antideslizantes");
            i.setPrice(13.59);
            i.setGender("unisex");
            i.setType("zapato");
            try {
                setItemImage(i, "/images/zapato_9.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });



        Item item50 = itemRepo.findByCode("ZAP10")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("ZAP10");
            i.setName("zapatilla de skate de lona");
            i.setDescription("con buena tracción, \naltas y con cordones");
            i.setPrice(17.56);
            i.setGender("unisex");
            i.setType("zapato");
            try {
                setItemImage(i, "/images/zapato_10.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });



        Item item51 = itemRepo.findByCode("ZAP11")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("ZAP11");
            i.setName("zapatos cómodos y casuales");
            i.setDescription("zapatos cómodos con suela suave bordada");
            i.setPrice(11.86);
            i.setGender("unisex");
            i.setType("zapato");
            try {
                setItemImage(i, "/images/zapato_11.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });



        Item item52 = itemRepo.findByCode("ZAP12")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("ZAP12");
            i.setName("zapatos de skate");
            i.setDescription("de caña baja vintage de color liso");
            i.setPrice(11.02);
            i.setGender("unisex");
            i.setType("zapato");
            try {
                setItemImage(i, "/images/zapato_12.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });



        Item item53 = itemRepo.findByCode("ZAP13")
        .orElseGet(() -> {
            Item i = new Item();
            i.setCode("ZAP13");
            i.setName("Pier One unisex");
            i.setDescription("Dark blue");
            i.setPrice(27.99);
            i.setGender("unisex");
            i.setType("zapato");
            try {
                setItemImage(i, "/images/zapato_13.PNG");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return itemRepo.save(i);
        });



     


        //--> save item into the db
        //Optional<Item> item01 = itemRepo.findByCode(item1.getCode());
        //if(!Optional<Item> item03 = itemRepo.findByCode(item3.getCode());


        //if(!item03.isPresent()) item01.isPresent())      
        //    itemRepo.save(item1);



        
        //1:1
        //create a size, 1 stock - 1 sizes
        Size size2 = sizeRepo.findByCode("SIZE2")
        .orElseGet(() -> {
            Size s = new Size();
            s.setCode("SIZE2");
            s.setLabel("M");
            return sizeRepo.save(s);
        });

        stock2.setSize(size2);


        Size size4 = sizeRepo.findByCode("SIZE4")
        .orElseGet(() -> {
            Size s = new Size();
            s.setCode("SIZE4");
            s.setLabel("36");
            return sizeRepo.save(s);
        });

        stock3.setSize(size4);

        

        //Optional<Clothes> stock02 = clothesRepo.findByCode(stock2.getCode());


        //if(!stock02.isPresent())
            //clothesRepo.save(stock2);

        // Optional<Clothes> stock03 = clothesRepo.findByCode(stock3.getCode());


        //if(!stock03.isPresent())
            //clothesRepo.save(stock3);
        
        clothesRepo.findByCode(stock4.getCode())
        .orElseGet(() -> clothesRepo.save(stock4));

        shoeRepo.findByCode(stock6.getCode())
                .orElseGet(() -> shoeRepo.save(stock6));

        shoeRepo.findByCode(stock7.getCode())
                .orElseGet(() -> shoeRepo.save(stock7));

        shoeRepo.findByCode(stock8.getCode())
                .orElseGet(() -> shoeRepo.save(stock8));

        shoeRepo.findByCode(stock9.getCode())
                .orElseGet(() -> shoeRepo.save(stock9));

        

            

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
