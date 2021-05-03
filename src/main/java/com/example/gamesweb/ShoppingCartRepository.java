package com.example.gamesweb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

    public List<ShoppingCart> findById (long id);

}
