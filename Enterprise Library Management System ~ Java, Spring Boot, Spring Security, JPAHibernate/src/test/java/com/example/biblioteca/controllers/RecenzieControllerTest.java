package com.example.biblioteca.controllers;

import com.example.biblioteca.dto.CarteRequest;
import com.example.biblioteca.dto.RecenzieRequest;
import com.example.biblioteca.mapper.CarteMapper;
import com.example.biblioteca.mapper.RecenzieMapper;
import com.example.biblioteca.model.*;
import com.example.biblioteca.services.CarteService;
import com.example.biblioteca.services.RecenzieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RecenzieController.class)
public class RecenzieControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RecenzieService recenzieService;
    @MockBean
    private RecenzieMapper recenzieMapper;


    @Test
    public void createCarte() throws Exception{
        Autor autor1 = new Autor(1,"testNume","testPrenume");
        CategorieCarte cat = new CategorieCarte(1,"test");
        Carte c = new Carte(1,"test",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"),autor1,cat);
        Cititor cit = new Cititor("test2","test","test","test","1231231231232",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"));


        RecenzieRequest request = new RecenzieRequest(1,"aa",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"),cit,c);
        when(recenzieService.saveRecenzie(any())).thenReturn(new Recenzie(1,"aa",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"),cit,c));
        mockMvc.perform(post("/recenzie")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rating").value(request.getRating()))
                .andExpect(jsonPath("$.review").value(request.getReview()));
    }

    @Test
    public void getAllRecenzie() throws Exception {

        Autor autor1 = new Autor(1,"testNume","testPrenume");
        CategorieCarte cat = new CategorieCarte(1,"test");
        Carte c = new Carte(1,"test",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"),autor1,cat);
        Cititor cit = new Cititor("test2","test","test","test","1231231231232",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"));


        Recenzie r1 = new Recenzie(1,"aa",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"),cit,c);
        Recenzie r2 = new Recenzie(2,"aaa",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2023-03-22T12:00:00"),cit,c);


        List<Recenzie> recenzieList = new ArrayList<>();
        recenzieList.add(r1);
        recenzieList.add(r2);

        when(recenzieService.getRecenzieAscCarte()).thenReturn(recenzieList);
        mockMvc.perform(get("/recenzie/asc-by-carte")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(recenzieList.size()))
                .andExpect(jsonPath("$[0].rating").value(recenzieList.get(0).getRating()))
                .andExpect(jsonPath("$[0].review").value(recenzieList.get(0).getReview()))
                .andExpect(jsonPath("$[1].rating").value(recenzieList.get(1).getRating()))
                .andExpect(jsonPath("$[1].review").value(recenzieList.get(1).getReview()));
    }

    @Test
    public void deleteRecenzie() throws Exception{
        Long id = 1L;
        recenzieService.deleteById(id);
        mockMvc.perform(delete("/recenzie/{id}",id)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

}
