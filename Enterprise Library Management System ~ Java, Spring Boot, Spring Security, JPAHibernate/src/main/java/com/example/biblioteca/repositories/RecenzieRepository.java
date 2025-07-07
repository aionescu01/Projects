package com.example.biblioteca.repositories;

import com.example.biblioteca.model.Recenzie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecenzieRepository extends JpaRepository<Recenzie,Long> {

    List<Recenzie> findAllByOrderByCarteAsc();

    List<Recenzie> findAllByOrderByCititorAsc();

}
