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


}