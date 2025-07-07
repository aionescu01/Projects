package com.example.biblioteca.services;

import com.example.biblioteca.model.*;
import com.example.biblioteca.repositories.AutorRepository;
import com.example.biblioteca.repositories.ImprumutRepository;
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
public class ImprumutServiceImplementationTest {

    @InjectMocks
    private ImprumutServiceImpl imprumutService;

    @Mock
    private ImprumutRepository imprumutRepository;

    @Test
    void testGetAllImprumut() throws ParseException {

        Autor a = new Autor(1,"test","test");
        CategorieCarte cat = new CategorieCarte(1,"test");
        Cititor cit = new Cititor(1,"test","test","test","test","1111111111111",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2000-03-27T12:00:00"));
        Carte c = new Carte(1,"test",a,cat);

        Imprumut imprumut1 = new Imprumut();
        imprumut1.setId(1);
        imprumut1.setData_imprumut(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"));
        imprumut1.setData_returnare(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-27T12:00:00"));
        imprumut1.setCarte(c);
        imprumut1.setCititor(cit);

        Imprumut imprumut2 = new Imprumut();
        imprumut2.setId(2);
        imprumut2.setData_imprumut(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"));
        imprumut2.setData_returnare(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-27T12:00:00"));
        imprumut2.setCarte(c);
        imprumut2.setCititor(cit);

        List<Imprumut> imprumuturiList = new ArrayList<>();
        imprumuturiList.add(imprumut1);
        imprumuturiList.add(imprumut2);

        when(imprumutRepository.findAll()).thenReturn(imprumuturiList);
        List<Imprumut> result = imprumutService.getAllImprumut();
        assertEquals(imprumuturiList.size(),result.size());
        assertEquals(imprumuturiList,result);
    }
}
