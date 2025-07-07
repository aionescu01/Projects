package com.example.biblioteca.services;

import com.example.biblioteca.model.*;
import com.example.biblioteca.repositories.AutorRepository;
import com.example.biblioteca.repositories.RecenzieRepository;
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
public class RecenzieServiceImplementationTest {

    @InjectMocks
    private RecenzieServiceImpl recenzieService;

    @Mock
    private RecenzieRepository recenzieRepository;

    @Test
    void testGetAllAutor() throws ParseException {

        Autor autor1 = new Autor(1,"testNume","testPrenume");
        CategorieCarte cat = new CategorieCarte(1,"test");
        Carte c = new Carte(1,"test",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"),autor1,cat);
        Cititor cit = new Cititor("test2","test","test","test","1231231231232",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"));


        Recenzie r1 = new Recenzie(1,"aa",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"),cit,c);
        Recenzie r2 = new Recenzie(2,"aaa",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"),cit,c);


        List<Recenzie> recenzieList = new ArrayList<>();
        recenzieList.add(r1);
        recenzieList.add(r2);

        when(recenzieRepository.findAll()).thenReturn(recenzieList);
        List<Recenzie> result = recenzieService.getAllRecenzie();
        assertEquals(recenzieList.size(),result.size());
        assertEquals(recenzieList,result);
    }
}
