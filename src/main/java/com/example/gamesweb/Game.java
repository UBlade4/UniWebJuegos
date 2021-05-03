package com.example.gamesweb;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    private String name;
    private String platform;
    private float price;
    private LocalDateTime date;
    private static int idManager = 1;

    public Game(String name, String platform, float price) {
        this.id = idManager;
        this.name = name;
        this.platform = platform;
        this.price = price;
        this.date = LocalDateTime.now();
        idManager++;
    }

    public Game() {
        this.id = idManager;
        this.name = null;
        this.platform = null;
        this.price = 0;
        this.date = LocalDateTime.now();
        idManager++;
    }

    @Id
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

}
