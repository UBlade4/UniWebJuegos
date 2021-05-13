package com.example.gamesweb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    public Rating findById(int id);

    public Optional<Rating> findByStars(String stars);

    public Optional<Rating> findByTitle(String title);

    public Optional<Rating> findByComment(String comment);

    public Optional<Rating> findByDate(String date);

    public List<Rating> findByIdManager(String idManager);

    public List <Rating> findAll ();


}
