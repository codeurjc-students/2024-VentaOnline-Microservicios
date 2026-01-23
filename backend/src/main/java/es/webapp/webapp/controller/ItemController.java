package es.webapp.webapp.controller;

import java.io.IOException;
import java.security.Principal;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.webapp.webapp.model.Item;
import es.webapp.webapp.model.ItemToBuy;
import es.webapp.webapp.model.User;
import es.webapp.webapp.service.ItemService;
import es.webapp.webapp.service.UserService;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

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

    @GetMapping("/{id}/page")
    public String itemPage(Model model, @PathVariable Integer id){   
            
        model.addAttribute("status","");
        Optional<Item> item = itemService.findById(id);
        if(!item.isPresent()) {
            return "error";
        }
        model.addAttribute("id", id);
        return "product";
    }

    @PostMapping("/{id}/purchase")
    public String itemBuy(Model model, ItemToBuy itemToBuy, @PathVariable Integer id, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) throws IOException{

        Optional<User> user = userService.findByUsername(userDetails.getUsername());
        if(user.isPresent()){
            if(itemService.addToCart(user.get().getUsername(), id, itemToBuy)){
                model.addAttribute("status","item successfully added to the cart");
                model.addAttribute("id", id);
                return "product";
            }else{
                model.addAttribute("status","failed to add the item to the cart");
                return "error";
            }
        }
        return "error";
    }

    @GetMapping("/{code}/favourites/{id}/new")
    public String addFavourite(Model model, @PathVariable String code, @PathVariable Integer id)  {
        Optional<Item> item = itemService.findByCode(code);
        Optional<User> user = userService.findById(id);
        
        item.get().getUsers().add(user.get());
        itemService.save(item.get());
        model.addAttribute("username",user.get().getUsername());
        return "index";

    }
}
