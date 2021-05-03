package com.example.gamesweb;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/")

public class GameRestController {

    @Autowired
    GameService service;

    ///////////////////////////  GAMES  ///////////////////////////

    //We get the game if it exists or an error if it doesn't
    @GetMapping("Game/{id}")
    public HttpEntity<?> getGame(@PathVariable int id) {
        Game game = service.getGame(id);
        if (game == null) {
            return new ResponseEntity<>("The game doesn't exist", HttpStatus.NOT_FOUND);

        } else {
            return new ResponseEntity<>(game, HttpStatus.OK);
        }
    }

    //We get all the games
    @GetMapping("Games")
    public HttpEntity<?> getGames() {
        if (service.getGames().isEmpty()) {
            return new ResponseEntity<>("There are no games", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(service.getGames(), HttpStatus.OK);
        }
    }

    @GetMapping("Games/Filter")
    public  HttpEntity<?> showGamesByPlatform(Model model, @RequestParam(value = "platform", defaultValue = "Any platform", required = false) String platform, @RequestParam(value = "pricemin", defaultValue = "0", required = false) int pricemin, @RequestParam(value = "pricemax", defaultValue = "0", required = false) int pricemax, @RequestParam(value = "stars", defaultValue = "-1", required = false) int stars ) {
        return new ResponseEntity<>(service.getGames(platform, pricemin, pricemax, stars), HttpStatus.OK);
    }

    //We publish our game
    @PostMapping("Games")
    @ResponseStatus(HttpStatus.CREATED)
    public Game newGame(@RequestBody Game game) {
        service.addGame(game.getId(), game);
        return game;
    }

    //It updates a game it it exists
    @PutMapping("/Game/{id}")
    public HttpEntity<?> updateGame(@PathVariable int id, @RequestBody Game updatedGame) {
        Game game = service.getGame(id);
        if (game == null) {
            return new ResponseEntity<>("The old game doesn't exist", HttpStatus.NOT_FOUND);
        } else {
            service.addGame(id, updatedGame);
            return new ResponseEntity<>(game, HttpStatus.OK);
        }
    }

    @PatchMapping("Game/{id}")
    public HttpEntity<?> modifyGame(@PathVariable int id, @RequestBody Game modifiedGame) {
        Game game = service.getGame(id);
        if (game == null) {
            return new ResponseEntity<>("The old game doesn't exist", HttpStatus.NOT_FOUND);
        } else {
            service.modifyGame(id, modifiedGame);
            return new ResponseEntity<>(service.getGame(id), HttpStatus.OK);
        }
    }

    //It deletes a game if it exists
    @DeleteMapping("/Game/{id}")
    public HttpEntity<?> deleteGame(@PathVariable int id) {
        Game game = service.getGame(id);
        if (game == null) {
            return new ResponseEntity<>("The game doesn't exist", HttpStatus.NOT_FOUND);
        } else {
            service.deleteGame(id);
            return new ResponseEntity<>(game, HttpStatus.OK);
        }
    }


    ///////////////////////////  RATINGS  ///////////////////////////


    //We get all ratings if they exist or an error if they don't
    @GetMapping("/Game/{id}/Ratings")
    public HttpEntity<?> getRequest(@PathVariable int id) {
        if (service.getGame(id) == null) {
            return new ResponseEntity<>("The game doesn't exist", HttpStatus.NOT_FOUND);
        }
        List<Rating> list = service.getRatings(id);
        if (list == null) {
            return new ResponseEntity<>("there are no ratings for this game yet", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    //We publish our rating
    @PostMapping("/Game/{id}/Ratings")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<?> newRating(@RequestBody Rating rating, @PathVariable int id) {
        if (service.getGame(id) == null) {
            return new ResponseEntity<>("The game doesn't exist", HttpStatus.NOT_FOUND);
        } else {
            List<Rating> list = service.getRatings(id);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(rating);
            service.addRating(id, list);
            return new ResponseEntity<>(rating, HttpStatus.OK);
        }
    }

    //It updates a rating if they exist
    @PutMapping("/Game/{id}/Ratings")
    public HttpEntity<?> updateRating(@PathVariable int id, @RequestBody Rating updatedRating) {
        List<Rating> rating = service.getRatings(id);
        if (rating == null) {
            return new ResponseEntity<>("The game doesn't exist", HttpStatus.NOT_FOUND);
        } else {
            for (Rating aux : rating) {
                if (aux.getId() == updatedRating.getId()) {
                    rating.remove(aux);
                    rating.add(updatedRating);
                    service.addRating(id, rating);
                    return new ResponseEntity<>(updatedRating, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("The rating doesn't exist", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/Game/{id}/Ratings")
    public HttpEntity<?> modifyRating(@PathVariable int id, @RequestBody Rating modifiedRating) {
        List<Rating> rating = service.getRatings(id);
        if (rating == null) {
            return new ResponseEntity<>("The game doesn't exist", HttpStatus.NOT_FOUND);
        } else {
            for (Rating aux : rating) {
                if (aux.getId() == modifiedRating.getId()) {
                    rating.remove(aux);
                    modifiedRating.combine(aux);
                    rating.add(modifiedRating);
                    service.addRating(id, rating);
                    return new ResponseEntity<>(modifiedRating, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("The rating doesn't exist", HttpStatus.NOT_FOUND);
        }
    }

    //It deletes all ratings if they exist
    @DeleteMapping("/Game/{id}/Ratings")
    public HttpEntity<?> deleteRatings(@PathVariable int id) {
        if (service.getGame(id) == null) {
            return new ResponseEntity<>("The game doesn't exist", HttpStatus.NOT_FOUND);
        }
        List<Rating> list = service.getRatings(id);
        if (list == null) {
            return new ResponseEntity<>("The rating list is already empty", HttpStatus.NOT_FOUND);
        } else {
            service.deleteRatings(id);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    //It deletes a rating if it exist
    @DeleteMapping("/Game/{id}/Rating")
    public HttpEntity<?> deleteRating(@PathVariable int id, @RequestParam int idRating) {
        if (service.getGame(id) == null) {
            return new ResponseEntity<>("The game doesn't exist", HttpStatus.NOT_FOUND);
        }
        List<Rating> list = service.getRatings(id);
        if (list == null) {
            return new ResponseEntity<>("There are no ratings for this game yet", HttpStatus.NOT_FOUND);
        } else {
            for (Rating aux : list) {
                if (aux.getId() == idRating) {
                    list.remove(aux);
                    service.addRating(id, list);
                    return new ResponseEntity<>(aux, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("The rating doesn't exist", HttpStatus.NOT_FOUND);
        }
    }


    ///////////////////////////  SHOPPING CART  ///////////////////////////


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