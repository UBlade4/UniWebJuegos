package com.example.gamesweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;
import java.util.List;


@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "cart", cascade=CascadeType.ALL)
    private List <Game> games;


    //Once a product is added to cart, we'll be redirected to a shown message telling us if it's been successfully done
    @GetMapping("/AddToCart")
    public String addToCart(Model model, @RequestParam int id) {

        //addToCart(id);
        return "AddedToCart.html";
    }

    public void removeGame(Game game) {
        this.games.remove(id);
    }
}
