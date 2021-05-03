package com.example.gamesweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {


    @Autowired
    GameService service;

    @PostConstruct
    public void init() {
        Game game1 = new Game("Minecraft", "PC", 20);
        Game game2 = new Game("Far Cry 5", "PlayStation", 60);
        Game game3 = new Game("Mario Bros", "Switch", 70);
        Game game4 = new Game("Planet coaster", "Xbox", 15);
        Game game5 = new Game("Fifa 12", "PlayStation", 10);
        service.addGame(game1.getId(), game1);
        service.addGame(game2.getId(), game2);
        service.addGame(game3.getId(), game3);
        service.addGame(game4.getId(), game4);
        service.addGame(game5.getId(), game5);

        List<Rating> list1 = new ArrayList<>();
        list1.add(new Rating(5, "Best in The world", "This game is the best ever!!!"));
        list1.add(new Rating(4, "Minecraft", "In Minecraft there are three modes" +
                " - survival, creative, and adventure. My favorite mode is survival because you have to start" +
                " from the beginning"));
        service.addRating(game1, list1);


        List<Rating> list2 = new ArrayList<>();
        list2.add(new Rating(3, "Best in The world", "Well its not better than FC3 or even FC4 ," +
                " the game is too repetitive, the AI is really bad, the environment that many people says its " +
                "beautiful is almost the same the entire game. The first 6 hours you may be having fun but then you " +
                "realize that ubisoft is making the same game over and over since 2012..."));
        service.addRating(game2, list2);
        List<Rating> list3 = new ArrayList<>();
        list3.add(new Rating(0, "Not recommended", "FIFA devs every year:\n" +
                "- Ctrl+C\n" +
                "- Ctrl+V"));
        list3.add(new Rating(1, "So bad", "FIFA 21 - Not for me this year. It never really changes except get worse." +
                " A lot of game bugs during matches that I can't be bothered going through.\n" +
                "\n" +
                "Luckily this was just a 10hr trial through EA Play. I would be going off my head if I purchased this full price.\n" +
                "\n" +
                "Honestly, don't bother and save your money."));
        service.addRating(game5, list3);

        List<Rating> list4 = new ArrayList<>();
        list4.add(new Rating(4, "I love it", "Does for RollerCoaster Tycoon. what Cities Skylines did for SimCity. Its the one to own"));
        service.addRating(game4, list4);
    }

    //When adding a new game, asked attributes will be: "name","platform" and "price"
    @GetMapping("/NewGame")
    public String newGame(Model model, @RequestParam String name, @RequestParam String platform, @RequestParam float price) {
        Game aux = new Game(name, platform, price);
        service.addGame(aux.getId(), aux);
        return "CreatedGame.html";
    }

    //Showing all games will be able in this address
    @GetMapping("/ShowGames")
    public String showGames(Model model) {
        model.addAttribute("games", service.getGames());
        return "ShowGames";
    }

    @GetMapping("/ShowGames/Filter")
    public String showGamesByPlatform(Model model,@RequestParam(value = "platform", defaultValue = "Any platform", required = false) String platform, @RequestParam(value = "pricemin", defaultValue = "0", required = false) int pricemin, @RequestParam(value = "pricemax", defaultValue = "0", required = false) int pricemax, @RequestParam(value = "stars", defaultValue = "-1", required = false) int stars ) {
        model.addAttribute("games", service.getGames(platform, pricemin, pricemax, stars));
        return "ShowGames";
    }

    //When choosing a game, whe show it's attributes or an error if it's not defined
    @GetMapping("/Game/{id}")
    public String showGames(Model model, @PathVariable int id) {
        model.addAttribute("id", id);
        Game game = this.service.getGame(id);
        if (game == null) {
            return "error/401";
        }
        model.addAttribute("game", game);
        model.addAttribute("average", service.getAverageRating(id));
        return "ShowGame";
    }

    //We are able to delete games and it's ratings and it will disappear from out shopping cart
    @GetMapping("/DeleteGame")
    public String deleteGame(Model model, @RequestParam int id) {
        service.deleteGame(id);
        return "DeletedGame.html";
    }

    @GetMapping("/Game/{id}/UpdateGame")
    public String modifyRating(Model model, @PathVariable int id) {
        model.addAttribute("id", id);
        return "UpdateGame";
    }

    @GetMapping("/ModifyGame")
        public String modifiedRating(Model model, @RequestParam int id, @RequestParam String platform, @RequestParam float price) {
        Game game = new Game(null, platform, price);
        service.modifyGame(id, game);
        return "ModifiedGame.html";
    }


}