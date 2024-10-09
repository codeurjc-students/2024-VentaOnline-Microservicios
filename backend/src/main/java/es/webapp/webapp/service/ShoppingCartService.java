package es.webapp.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.webapp.webapp.model.ShoppingCart;
import es.webapp.webapp.repository.ShoppingCartRepo;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepo shoppingCartRepo;

    public void save(ShoppingCart shoppingCart){
        shoppingCartRepo.save(shoppingCart);
    }
    
}
