package com.example.biblioteca.repositories;

import com.example.biblioteca.model.Imprumut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImprumutRepository extends JpaRepository<Imprumut,Long> {

    List<Imprumut> findAllByOrderByIdAsc();

}
