package com.example.gamesweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

public class RatingRESTController {

    @Autowired
    GameService service;


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

}
