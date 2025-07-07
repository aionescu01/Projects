package com.example.biblioteca.services;

import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.CategorieCarte;
import com.example.biblioteca.repositories.AutorRepository;
import com.example.biblioteca.repositories.CategorieCarteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategorieCarteImplemetationTest {

    @InjectMocks
    private CategorieCarteServiceImpl categorieCarteService;

    @Mock
    private CategorieCarteRepository categorieCarteRepository;

    @Test
    void testGetAllCategorieCarte() throws ParseException {

        CategorieCarte cat = new CategorieCarte(1,"test");
        CategorieCarte cat2 = new CategorieCarte(2,"test2");

        List<CategorieCarte> categorieCarteList = new ArrayList<>();
        categorieCarteList.add(cat);
        categorieCarteList.add(cat2);

        when(categorieCarteRepository.findAll()).thenReturn(categorieCarteList);
        List<CategorieCarte> result = categorieCarteService.getAllCategorieCarte();
        assertEquals(categorieCarteList.size(),result.size());
        assertEquals(categorieCarteList,result);
    }



}
