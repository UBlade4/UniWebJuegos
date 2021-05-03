package com.example.gamesweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    private Map<Integer, Game> games = new HashMap<>();
    private Map<Game, List<Rating>> ratings = new HashMap<>();
    private Set<Game> shoppingCart = new HashSet<>();

    public Game addGame(int id, Game game){
        return gameRepository.save(game);
        /*
        game.setId(id);
        this.games.put(id, game);
        this.ratings.put(game, new ArrayList<Rating>());*/
    }

    public Collection<Game> getGames(){
        return this.games.values();
    }


    public Game getGame(int id){
        return this.games.get(id);
    }

    public void deleteGame(int id) {
        Game var;
        Optional<Game> varList = gameRepository.findById(id);
        if (!varList.isPresent()) {
            var = varList.get();
            gameRepository.delete(var);
        }
    }
       /* Game aux = this.games.get(id);
        this.games.remove(id);
        this.ratings.remove(aux);
        this.shoppingCart.remove(aux);
        return aux;*/


    public Game modifyGame(int id, Game modifiedGame) {
        Game game = this.games.get(id);
        if(modifiedGame.getName() == null){
            modifiedGame.setName(game.getName());
        }
        if(modifiedGame.getPrice() == 0) {
            modifiedGame.setPrice(game.getPrice());
        }
        if(modifiedGame.getPlatform() == null){
            modifiedGame.setPlatform(game.getPlatform());
        }

        modifiedGame.setId(id);
        this.games.put(id, modifiedGame);

        this.ratings.put(modifiedGame, this.ratings.get(game));
        return modifiedGame;
    }



}
