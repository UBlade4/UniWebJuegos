package com.example.gamesweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;



    public int getAverageRating(int id){
        int counter = 0;
        int total = 0;
        for(Rating rating : this.ratingRepository.findAll()){
            total += rating.getStars();
            counter++;
        }
        if(counter != 0) {
            return total / counter;
        } else {
            return 0;
        }
    }


    public List<Rating> getRatings (int id,Game game){
        if(this.ratingRepository.findById(id)==null){
            return null;
        }
        else {
            return ratingRepository.findAll();
        }
    }

    public void addRating(int id,Rating rating){
        List<Rating> aux = this.getRatings(id);
        if (aux == null) {
            aux = new ArrayList<>();
        }
        aux.add(rating);
        this.addRating(id,this.ratingRepository.findById(id));
    }
}
