package com.example.gamesweb;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String name;
    @Column
    private String platform;
    @Column
    private float price;
    @Column
    private LocalDateTime date;
    @Column
    private static int idManager = 1;


    @ManyToMany(mappedBy = "games", cascade=CascadeType.ALL)
    private List<Category> categories;


    @ManyToOne
    private ShoppingCart shoppingCart;

    @JsonIgnore
    @OneToMany (mappedBy = "games")
    private List<Rating> ratings;



    public Game(String name, String platform, float price) {
        this.id = idManager;
        this.name = name;
        this.platform = platform;
        this.price = price;
        this.date = LocalDateTime.now();
        this.ratings = new ArrayList<>();
        idManager++;
    }

    public Game() {
        this.id = idManager;
        this.name = null;
        this.platform = null;
        this.price = 0;
        this.date = LocalDateTime.now();
        this.ratings = new ArrayList<>();
        idManager++;
    }


    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void addRating(Rating rating){
        this.ratings.add(rating);
    }

}
