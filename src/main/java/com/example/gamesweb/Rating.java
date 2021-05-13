package com.example.gamesweb;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private static int idManager = 1;
    @Column
    private int stars;
    @Column
    private String title;
    @Column
    private String comment;
    @Column
    private final LocalDateTime date;

    @ManyToOne
    private List<Game> games;


    public Rating(int stars, String title, String comment) {

        this.id = idManager;
        this.stars = stars;
        this.title = title;
        this.comment = comment;
        this.date = LocalDateTime.now();
        idManager++;
    }

    public Rating(LocalDateTime date) {

        this.date = date;
    }

    public Rating() {

        date = null;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStars() { return stars; }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void combine(Rating ori) {
        if(this.stars == 0) {
            this.setStars(ori.getStars());
        }
        if(this.title == null) {
            this.setTitle(ori.getTitle());
        }
        if(this.comment == null) {
            this.setComment(ori.getComment());
        }
    }
}
