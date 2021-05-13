package com.example.gamesweb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Integer> {

    public Game findById(int id);

    public List<Game> findByName(String name);

    public List<Game> findByPrice(float price);

    public List<Game> findByPlatform(String platform);

    public List<Game> findByDate(String date);

    public List<Game> findByIdManager(int idManager);

    public List<Game> findAllById (long id);

}
