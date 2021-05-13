package com.example.gamesweb;


import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idCategory;
    @Column(nullable = false)
    private String nameCategory;
    @Column
    private static int idManager = 1;

    @ManyToMany
    private List<Game> games = new ArrayList<>();

    public Category(int id, String name){
        super();
        this.idCategory = idManager;
        this.nameCategory = name;
        idManager++;
    }

    public Category() {
        this.idCategory=idManager;
        this.nameCategory=null;

    }



    public long getId(){return idCategory;}
    public void setId(long id){this.idCategory=id;}

    public String getName(){return nameCategory;}
    public void setName(String name){this.nameCategory=name;}




}
