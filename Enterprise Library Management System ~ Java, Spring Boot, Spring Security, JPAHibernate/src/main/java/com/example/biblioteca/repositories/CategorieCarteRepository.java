package com.example.biblioteca.repositories;

import com.example.biblioteca.model.CategorieCarte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategorieCarteRepository extends JpaRepository<CategorieCarte,Long> {

    List<CategorieCarte> findAllByOrderByNumeAsc();

    List<CategorieCarte> findAllByOrderByNumeDesc();

}
