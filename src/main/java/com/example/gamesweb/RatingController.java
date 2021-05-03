package com.example.gamesweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public class RatingController {

    @Autowired
    GameService service;


    //We can see a game's ratings. If it doesn't exist, we'll see an error
    @GetMapping("/Game/{id}/Ratings")
    public String showRating(Model model, @PathVariable int id) {
        model.addAttribute("id", id);
        if (service.getGame(id) == null) {
            return "error/401";
        }
        if (service.getRatings(id).isEmpty()) {
            model.addAttribute("empty", true);
        } else {
            model.addAttribute("empty", false);
        }
        model.addAttribute("gameid", id);
        model.addAttribute("ratings", service.getRatings(id));
        return "ShowRatings";
    }

    //We'll be redirected to "NewRating" related to a game and it's id
    @GetMapping("/Game/{id}/Ratings/CreateRating")
    public String ratingCreation(Model model, @PathVariable int id) {
        if (service.getGame(id) == null) {
            return "error/401";
        }
        model.addAttribute("id", id);
        model.addAttribute("game", service.getGame(id));
        return "NewRating";
    }


    //We are able to create ratings, adding a title, comments and a rating with stars
    @GetMapping("/NewRating")
    public String newRating(Model model, @RequestParam String title, @RequestParam String comment, @RequestParam int stars, @RequestParam int id) {

        Rating rating = new Rating(stars, title, comment);
        service.addRating(id, rating);
        return "CreatedRating.html";
    }

    @GetMapping("/DeleteRating")
    public String deleteRating(Model model, @RequestParam int id, @RequestParam int gameid) {
        service.deleteRating(id, gameid);
        return "DeletedRating.html";
    }


}
