package com.example.gamesweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

public class ShoppingCartRESTController {

    @Autowired
    GameService service;

    //We get our shopping cart if it's not empty
    @GetMapping("/ShoppingCart")
    public HttpEntity<?> getShoppingCart() {
        if (service.getShoppingCart().isEmpty()) {
            return new ResponseEntity<>("The shopping cart is empty", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(service.getShoppingCart(), HttpStatus.OK);
        }
    }

    //We post the elements of out shopping cart if it's not empty
    @PostMapping("/ShoppingCart")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<?> newGameShoppingCart(@RequestParam int id) {
        Game game = service.getGame(id);
        if (game == null) {
            return new ResponseEntity<>("The game doesn't exist", HttpStatus.NOT_FOUND);
        } else {
            service.addToCart(id);
            return new ResponseEntity<>(game, HttpStatus.OK);
        }
    }

    //It deletes a game from the shopping cart if it's not empty
    @DeleteMapping("/ShoppingCart")
    public HttpEntity<?> deleteGameShoppingCart(@RequestParam int id) {
        Game game = service.getGame(id);
        if (game == null) {
            return new ResponseEntity<>("The game doesn't exist", HttpStatus.NOT_FOUND);
        } else {
            if (service.getShoppingCart().contains(game)) {
                service.removeShoppingCart(id);
                return new ResponseEntity<>(game, HttpStatus.OK);
            }
            return new ResponseEntity<>("The game isn't in the sopping cart", HttpStatus.NOT_FOUND);
        }
    }

    //It deletes all elements of our shopping cart
    @DeleteMapping("/ShoppingCart/All")
    public HttpEntity<?> deleteShoppingCart() {
        Set<Game> copy = service.getShoppingCart();
        if (copy.isEmpty()) {
            return new ResponseEntity<>("The shopping cart is empty", HttpStatus.NOT_FOUND);
        }
        service.removeShoppingCart();
        return new ResponseEntity<>(copy, HttpStatus.OK);
    }
}
