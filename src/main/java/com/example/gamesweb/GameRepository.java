package com.example.gamesweb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Integer> {

    public List<Game> findById(String id);

    public List<Game> findByName(String name);

    public List<Game> findByPrice(String price);

    public List<Game> findByPlatform(String platform);

    public List<Game> findByDate(String date);

    public List<Game> findByIdManager(String idManager);

    public List<Game> findAllById (long id);

}
