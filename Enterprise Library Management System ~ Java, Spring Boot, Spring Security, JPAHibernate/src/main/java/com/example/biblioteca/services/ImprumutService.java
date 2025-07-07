package com.example.biblioteca.services;

import com.example.biblioteca.dto.ImprumutUpdateDto;
import com.example.biblioteca.model.Imprumut;
import com.example.biblioteca.model.Recenzie;

import java.util.List;
public interface ImprumutService {

    Imprumut saveImprumut(Imprumut imprumut);
    List<Imprumut> getImprumutAscId();

    Imprumut updateImprumut(Long id, ImprumutUpdateDto imprumut);

    void deleteById(Long id);

    List<Imprumut> getAllImprumut();

}
