package com.example.biblioteca.repositories;

import com.example.biblioteca.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Long> {

    List<Autor> findAllByOrderByNumeAsc();

    List<Autor> findAllByOrderByNumeDesc();
}
