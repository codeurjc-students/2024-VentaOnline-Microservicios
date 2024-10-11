package es.webapp.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.Order;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.ItemToBuyService;
import es.webapp.webapp.service.OrderService;
import es.webapp.webapp.service.UserService;

@Controller
@RequestMapping("/orders")
public class OrderController {

     @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemToBuyService itemToBuyService;

    @ModelAttribute
    public void addAttribute(Model model, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();

        if(principal != null){
            String name = principal.getName();
            Optional<User> user = userService.findByUsername(name);
            model.addAttribute("username",user.get().getUsername());
            model.addAttribute("admin", request.isUserInRole("ADMIN"));
            model.addAttribute("user", request.isUserInRole("USER"));
            model.addAttribute("logged",true);
        } else {
            model.addAttribute("logged",false);
        }
        model.addAttribute("order","");
    }

    @GetMapping("/new")
    public String generate(Model model,  HttpServletRequest request) throws IOException {
        Order order = new Order();

        Principal principal = request.getUserPrincipal();

        if(principal != null){
            String name = principal.getName();
            Optional<User> user = userService.findByUsername(name);
            List<ItemToBuy> itemsToBuy = itemToBuyService.findByShoppingCart(user.get().getShoppingCart());
       
            double cost=0;
            for(ItemToBuy item: itemsToBuy){
                cost += item.getItem().getPrice();
                item.setOrder(order);
            }

            order.setTotalCost(cost);
            order.setState("PENDING");
            order.setCreationDate(LocalDate.now());
            order.setUser(user.get());
            orderService.save(order);
            model.addAttribute("order","order successfully created");

            return "shoppingCart";
        } 
        return "error";
    }
}
