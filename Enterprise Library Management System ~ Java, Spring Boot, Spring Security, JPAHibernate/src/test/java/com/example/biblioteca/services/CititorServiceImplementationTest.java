package com.example.biblioteca.services;

import com.example.biblioteca.exception.DuplicateCititorException;
import com.example.biblioteca.model.*;
import com.example.biblioteca.repositories.CititorRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CititorServiceImplementationTest {

    @InjectMocks
    private CititorServiceImpl cititorService;

    @Mock
    private CititorRepository cititorRepository;

    @Test
    void whenCititorAlreadyExists_saveCititor_throwsDuplicateCititorException() throws ParseException {

        Cititor cit = new Cititor(1,"test","test","test","test","1111111111111",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2000-03-27T12:00:00"));
        when(cititorRepository.findByCNP(cit.getCNP()))
                .thenReturn(Optional.of(cit));
        DuplicateCititorException exception = assertThrows(DuplicateCititorException.class,
                () -> cititorService.saveCititor(cit));
        assertEquals("Exista un cititor cu acest CNP!",exception.getMessage());
        verify(cititorRepository,times(0)).save(cit);
    }

    @Test
    void whenCititorDoesntExist_saveCititor_savesTheCititor() throws ParseException {


        Cititor cit = new Cititor(1,"test","test","test","test","1111111111111",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2000-03-27T12:00:00"));


        when(cititorRepository.findByCNP(cit.getCNP()))
                .thenReturn(Optional.empty());

        Cititor cit2 = new Cititor(1,"test","test","test","test","1111111111111",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2000-03-27T12:00:00"));


        when(cititorRepository.save(cit)).thenReturn(cit2);

        Cititor result = cititorService.saveCititor(cit);

        assertNotNull(result);
        assertEquals(cit2.getId(),result.getId());

        assertEquals(cit2.getCNP(),result.getCNP());
        assertEquals(cit.getCNP(),result.getCNP());

        verify(cititorRepository).findByCNP(cit.getCNP());
        verify(cititorRepository).save(cit);
    }

    @Test
    void testGetAllCititor() throws ParseException {

        Cititor cit = new Cititor(1,"test","test","test","test","1111111111111",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2000-03-27T12:00:00"));


        Cititor cit2 = new Cititor(2,"test","test","test","test","1111111111111",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2000-03-27T12:00:00"));


        List<Cititor> cititorList = new ArrayList<>();;
        cititorList.add(cit);
        cititorList.add(cit2);

        when(cititorRepository.findAll()).thenReturn(cititorList);

        List<Cititor> result = cititorService.getAllCititor();

        assertEquals(cititorList.size(),result.size());
        assertEquals(cititorList,result);

    }

}
