package com.example.biblioteca.repositories;

import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.Carte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarteRepository extends JpaRepository<Carte,Long> {

    List<Carte> findAllByOrderByTitluAsc();

    List<Carte> findAllByOrderByTitluDesc();
}
