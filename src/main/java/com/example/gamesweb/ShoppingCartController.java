package com.example.gamesweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



public class ShoppingCartController {


    @Autowired
    private ShoppingCartService service;

    @Autowired
    GameService gameService;


        //It'll show us our shopping cart or a message telling us it's empty otherwise
        @GetMapping("/ShoppingCart")
        public String showShoppingCart(Model model, int id) {
            model.addAttribute("cart", service.getShoppingCart(id));
            if (service.getShoppingCart(id)==null) {
                model.addAttribute("empty", true);
            } else {
                model.addAttribute("empty", false);
            }
            int sum = 0;
            for (Game game : this.gameService.getGames() ) {
                sum += game.getPrice();
            }
            model.addAttribute("sum", sum);
            return "ShoppingCart";
        }


        //We can remove a game from our shopping cart if we regret our decision of buying it
        @GetMapping("/RemoveGame")
        public String removeGameShoppingCart(Model model, @RequestParam int id) {
            service.removeShoppingCart2(id, gameService.getGame(id));
            return "RemovedGame.html";
        }

        //We can delete all items from our shopping cart
        @GetMapping("/DeleteShoppingCart")
        public String removeShoppingCart(Model model) {
            service.deleteShoppingCart();
            return "RemovedShoppingCart.html";
        }


    }

