package com.example.gamesweb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    public Optional<Category> findByIdCategory (long id);

    public Optional<Category> findByNameCategory (String name);

    public List<Category> findAllByIdCategory (long id);

}

