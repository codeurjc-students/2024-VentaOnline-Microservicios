package es.webapp.webapp.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.ItemToBuyService;
import es.webapp.webapp.service.UserService;

@Controller
@RequestMapping("/shoppingcart")
public class ShoppingCartController {

    @Autowired
    private UserService userService;

    @Autowired
    private ItemToBuyService itemToBuyService;

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) {

        Principal principal = request.getUserPrincipal();
        
        if(principal != null) {
            model.addAttribute("logged", true);
            model.addAttribute("username", principal.getName());
            model.addAttribute("admin", request.isUserInRole("ADMIN"));
            model.addAttribute("user", request.isUserInRole("USER"));
            Optional<User> userSession = userService.findByUsername(userDetails.getUsername());
            model.addAttribute("id", userSession.get().getId());
        } else {
            model.addAttribute("logged", false);
        }
    }

    @GetMapping("/page")
    public String shoppingCartPage(Model model, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails){

        Optional<User> user = userService.findByUsername(userDetails.getUsername());
        if(user.isPresent()) {
            List<ItemToBuy> itemsToBuy = itemToBuyService.findByShoppingCart(user.get().getShoppingCart());
            if(itemsToBuy.size() > 0){
                model.addAttribute("neworder",false);
            } else {
                model.addAttribute("neworder",true);
            }
            return "shoppingCart";
        } else {
            return "error";
        }     
    }

    @GetMapping("/{id}/remove")
    public String removeItemToBuy(Model model, @PathVariable Integer id){

        Optional<ItemToBuy> itemToBuy = itemToBuyService.findById(id);

        User user = itemToBuy.get().getShoppingCart().getUser();

        itemToBuyService.updateShoppingCart(itemToBuy.get());

        if(itemToBuy.isPresent()) {
            itemToBuyService.deleteById(id);
        }

        List<ItemToBuy> itemsToBuy = itemToBuyService.findByShoppingCart(user.getShoppingCart());
        if(itemsToBuy.size() > 0){
            model.addAttribute("neworder",false);
        } else {
            model.addAttribute("neworder",true);
        }
        return "shoppingCart";
    }
    
}
