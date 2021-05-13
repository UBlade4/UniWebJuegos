package com.example.gamesweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;


    public Game addGame(Game game){
        return gameRepository.saveAndFlush(game);
        /*
        game.setId(id);
        this.games.put(id, game);
        this.ratings.put(game, new ArrayList<Rating>());*/
    }

    public Collection<Game> getGames(){
        return this.gameRepository.findAll();
    }


    public Game addRating(int id, Rating rating){
        Game game = gameRepository.findById(id);
        game.addRating(rating);
        this.gameRepository.saveAndFlush(game);
        return game;

    }


    public Game getGame(int id){
        if(this.gameRepository.findById(id)==null){
            return null;
        }
        else {
            return this.gameRepository.findById(id);
        }
    }

    public void deleteGame(int id) {
        if(this.gameRepository.findById(id)!=null){
            gameRepository.delete(this.gameRepository.findById(id));
        }
    }
       /* Game aux = this.games.get(id);
        this.games.remove(id);
        this.ratings.remove(aux);
        this.shoppingCart.remove(aux);
        return aux;*/


    public Game modifyGame(int id, Game modifiedGame) {
        Game game = this.gameRepository.findById(id);


        modifiedGame.setId(id);
        modifiedGame.setRatings(game.getRatings());
        this.gameRepository.saveAndFlush(modifiedGame);

        return modifiedGame;
    }

}
