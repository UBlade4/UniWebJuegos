package com.example.gamesweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Id;
import java.util.List;


public class ShoppingCartService{

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    private List<Game> games;
    @Id
    private int id;


    public Object getShoppingCart(int id) {
        return shoppingCartRepository.findById(id);
    }

    public List<ShoppingCart> getShoppingCart() {
        return shoppingCartRepository.findAll();
    }

    public void removeShoppingCart2(int id,Game game) {
        ShoppingCart cart = shoppingCartRepository.findById(id);
        cart.removeGame(game);
        shoppingCartRepository.saveAndFlush(cart);

    }

    public void deleteShoppingCart() {
        //ShoppingCart cart = shoppingCartRepository.findById(id);
        shoppingCartRepository.deleteAll();
        //shoppingCartRepository.delete(cart);
        //shoppingCartRepository.saveAndFlush(cart);
    }
}


