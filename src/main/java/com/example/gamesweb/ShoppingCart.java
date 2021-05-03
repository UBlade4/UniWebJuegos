package com.example.gamesweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class ShoppingCart {

    @Autowired
    GameService service;

    //Once a product is added to cart, we'll be redirected to a shown message telling us if it's been successfully done
    @GetMapping("/AddToCart")
    public String addToCart(Model model, @RequestParam int id) {
        service.addToCart(id);
        return "AddedToCart.html";
    }


    //It'll show us our shopping cart or a message telling us it's empty otherwise
    @GetMapping("/ShoppingCart")
    public String showShoppingCart(Model model) {
        model.addAttribute("cart", service.getShoppingCart());
        if (service.getShoppingCart().isEmpty()) {
            model.addAttribute("empty", true);
        } else {
            model.addAttribute("empty", false);
        }
        int sum = 0;
        for (Game game : service.getShoppingCart()) {
            sum += game.getPrice();
        }
        model.addAttribute("sum", sum);
        return "ShoppingCart";
    }


    //We can remove a game from our shopping cart if we regret our decision of buying it
    @GetMapping("/RemoveGame")
    public String removeGameShoppingCart(Model model, @RequestParam int id) {
        service.removeShoppingCart(id);
        return "RemovedGame.html";
    }

    //We can delete all items from our shopping cart
    @GetMapping("/DeleteShoppingCart")
    public String removeShoppingCart(Model model) {
        service.deleteShoppingCart();
        return "RemovedShoppingCart.html";
    }
}
