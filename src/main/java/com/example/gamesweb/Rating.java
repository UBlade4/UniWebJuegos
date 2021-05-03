package com.example.gamesweb;

import java.time.LocalDateTime;

public class Rating {

    private static int idManager = 1;
    private int id;
    private int stars;
    private String title;
    private String comment;
    private final LocalDateTime date;

    public Rating(int stars, String title, String comment) {

        this.id = idManager;
        this.stars = stars;
        this.title = title;
        this.comment = comment;
        this.date = LocalDateTime.now();
        idManager++;
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
