package com.example.biblioteca.repositories;


import com.example.biblioteca.model.Cititor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface CititorRepository extends JpaRepository<Cititor,Long> {

    List<Cititor> findAllByOrderByNumeAsc();

    List<Cititor> findAllByOrderByNumeDesc();

    Optional<Cititor> findByCNP(@NotNull @Pattern(regexp = "\\d{13}", message = "CNP incorect") String CNP);

}
