package com.example.gamesweb;

import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class GameService {

    private Map<Integer, Game> games = new HashMap<>();
    private Map<Game, List<Rating>> ratings = new HashMap<>();
    private Set<Game> shoppingCart = new HashSet<>();

    public void addGame(int id, Game game){
        game.setId(id);
        this.games.put(id, game);
        this.ratings.put(game, new ArrayList<Rating>());
    }

    public Collection<Game> getGames(){
        return this.games.values();
    }

    public Collection<Game> getGames(String platform, int pricemin, int pricemax, int stars){
        List<Game> aux = new ArrayList<>();
        List<Game> aux2 = new ArrayList<>();

        for(Game game : this.games.values()){
            if(game.getPlatform().equals(platform) || platform.equals("Any platform")){
                aux.add(game);
            }
        }

        if(pricemin != 0 && pricemax == 0) {
            for (Game game : aux) {
                if (game.getPrice() >= pricemin ) {
                    aux2.add(game);
                }
            }
            aux=aux2;
        } else if(pricemin == 0 && pricemax != 0) {
            for (Game game : aux) {
                if (game.getPrice() <= pricemax) {
                    aux2.add(game);
                }
            }
            aux=aux2;
        } else if(pricemin < pricemax) {
            for (Game game : aux) {
                if (game.getPrice() >= pricemin && game.getPrice() <= pricemax) {
                    aux2.add(game);
                }
            }
            aux=aux2;
        }

        if(stars > 0 && stars < 6) {
            for (Game game : aux) {
                if (this.getAverageRating(game.getId()) == stars) {
                    aux2.add(game);
                }
            }
            aux=aux2;
        }


        return aux;
    }

    public Game getGame(int id){
        return this.games.get(id);
    }

    public Game deleteGame(int id){
        Game aux = this.games.get(id);
        this.games.remove(id);
        this.ratings.remove(aux);
        this.shoppingCart.remove(aux);
        return aux;
    }

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

    public void addRating(Game game, List<Rating> lists){
        this.ratings.put(game, lists);
    }

    public void addRating(int id, List<Rating> lists){
        this.ratings.put(this.getGame(id), lists);
    }


    public void addRating(int id, Rating rating){
        List<Rating> aux = this.getRatings(id);
        if (aux == null) {
            aux = new ArrayList<>();
        }
        aux.add(rating);
        this.addRating(this.games.get(id), aux);
    }

    public List<Rating> getRatings(int id){ return this.ratings.get(this.games.get(id));}

    public int getAverageRating(int id){
        int counter = 0;
        int total = 0;
        for(Rating rating : this.getRatings(id)){
            total += rating.getStars();
            counter++;
        }
        if(counter != 0) {
            return total / counter;
        } else {
            return 0;
        }
    }

    public Rating deleteRating(int id, int gameid){
        for(Rating aux : this.ratings.get(this.games.get(gameid))){
            if(aux.getId()== id){
                this.ratings.get(this.games.get(gameid)).remove(aux);
                return aux;
            }
        }
        return null;
    }

    public List<Rating> deleteRatings(int id){
        List<Rating> aux = this.getRatings(id);
        this.addRating(id, new ArrayList<>());
        return aux;
    }

    public void addToCart(int id){
        this.shoppingCart.add(this.games.get(id));
    }

    public Set<Game> getShoppingCart(){
        return this.shoppingCart;
    }

    public Set<Game> removeShoppingCart(int id){
        Set<Game> aux = this.shoppingCart;
        this.shoppingCart.remove(this.games.get(id));
        return aux;
    }

    public Set<Game> removeShoppingCart(){
        Set<Game> aux = this.shoppingCart;
        this.shoppingCart = new HashSet<>();
        return aux;
    }

    public void deleteShoppingCart(){
        this.shoppingCart.clear();
    }
}
