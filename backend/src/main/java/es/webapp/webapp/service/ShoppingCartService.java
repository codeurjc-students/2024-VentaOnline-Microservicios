package es.webapp.webapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.webapp.webapp.model.ShoppingCart;
import es.webapp.webapp.model.User;
import es.webapp.webapp.repository.ShoppingCartRepo;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepo shoppingCartRepo;

    public void save(ShoppingCart shoppingCart){
        shoppingCartRepo.save(shoppingCart);
    }

    public Optional<ShoppingCart> findByUser(User user){
        return shoppingCartRepo.findByUser(user);
    }

    public Optional<ShoppingCart> findByCode(String code){
        return shoppingCartRepo.findByCode(code);
    }
}
