package com.example.gamesweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;


    public void addRating(Game game, List<Rating> lists){ return ratingRepository.save(rating);}

    public void addRating(int id, List<Rating> lists){ return ratingRepository.save();}


    public void addRating(int id, Rating rating){
        List<Rating> aux = this.getRatings(id);
        if (aux == null) {
            aux = new ArrayList<>();
        }
        aux.add(rating);
        this.addRating(this.games.get(id), aux);
    }

    public List<Rating> getRatings(int id){ return ratingRepository.findById(id);}

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
        for(Rating aux : this.ratings.get(return GameRepository.findById(id))){
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


}
