package com.example.biblioteca.services;

import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.Cititor;
import com.example.biblioteca.repositories.AutorRepository;
import com.example.biblioteca.repositories.CititorRepository;
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
public class AutorServiceImplementationTest {

    @InjectMocks
    private AutorServiceImpl autorService;

    @Mock
    private AutorRepository autorRepository;

    @Test
    void testGetAllAutor() throws ParseException {

        Autor autor1 = new Autor(1,"testNume","testPrenume");
        Autor autor2 = new Autor(2,"testNume2","testPrenume2");

        List<Autor> autorList = new ArrayList<>();
        autorList.add(autor1);
        autorList.add(autor2);

        when(autorRepository.findAll()).thenReturn(autorList);
        List<Autor> result = autorService.getAllAutor();
        assertEquals(autorList.size(),result.size());
        assertEquals(autorList,result);
    }
}
