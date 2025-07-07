package com.example.biblioteca.services;

import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.Carte;
import com.example.biblioteca.model.CategorieCarte;
import com.example.biblioteca.repositories.AutorRepository;
import com.example.biblioteca.repositories.CarteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarteServiceImplementationTest {

    @InjectMocks
    private CarteServiceImpl carteService;

    @Mock
    private CarteRepository carteRepository;

    @Test
    void testGetAllCarte() throws ParseException {

        Autor autor1 = new Autor(1,"testNume","testPrenume");
        CategorieCarte cat = new CategorieCarte(1,"test");


        Carte carte = new Carte("test",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T10:00:00"),autor1,cat);
        Carte carte2 = new Carte("test2",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T10:00:00"),autor1,cat);


        List<Carte> carteList = new ArrayList<>();
        carteList.add(carte);
        carteList.add(carte2);

        when(carteRepository.findAll()).thenReturn(carteList);
        List<Carte> result = carteService.getAllCarte();
        assertEquals(carteList.size(),result.size());
        assertEquals(carteList,result);
    }
}
