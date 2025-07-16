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
import es.webapp.webapp.model.Order.State;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.ItemToBuyService;
import es.webapp.webapp.service.OrderService;
import es.webapp.webapp.service.UserService;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private ItemToBuyService itemToBuyService;

    @Autowired
    private OrderService orderService;

    @ModelAttribute
    public void addAttribute(Model model, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();

        if(principal != null){
            String name = principal.getName();
            Optional<User> user = userService.findByUsername(name);
            model.addAttribute("username",user.get().getUsername());
            model.addAttribute("id",user.get().getId());
            model.addAttribute("admin", request.isUserInRole("ADMIN"));
            model.addAttribute("user", request.isUserInRole("USER"));
            model.addAttribute("logged",true);
            List<ItemToBuy> itemsToBuy = itemToBuyService.findByShoppingCart(user.get().getShoppingCart());
            if(itemsToBuy.size() > 0){
                model.addAttribute("neworder",false);
            }
        } else {
            model.addAttribute("logged",false);
        }
        
        model.addAttribute("order","");
    }

    @GetMapping("/new/users/{username}")
    public String generate(Model model, @PathVariable String username) throws IOException {
        Order order = new Order();

        Optional<User> user = userService.findByUsername(username);

        if(user.isPresent()) {
            List<ItemToBuy> itemsToBuy = itemToBuyService.findByShoppingCart(user.get().getShoppingCart());
       
            double cost=0.0;
            for(ItemToBuy item: itemsToBuy){
                cost += (item.getCount()*item.getItems().get(0).getPrice());
                item.setOrder(order);
                item.setShoppingCart(null);
            }

            user.get().getShoppingCart().setTotalCost(0.0);

            order.setTotalCost(cost);
            order.setCode("AS33O4S");
            order.setState(State.PENDING);
            order.setCreationDate(LocalDate.now());
            order.setUser(user.get());
            orderService.save(order);

            return "index";
        } 
        return "error";
    }

}
