package com.example.biblioteca.controllers;

import com.example.biblioteca.dto.AutorRequest;
import com.example.biblioteca.mapper.AutorMapper;
import com.example.biblioteca.model.Autor;
import com.example.biblioteca.services.AutorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AutorController.class)
public class AutorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AutorService autorService;
    @MockBean
    private AutorMapper autorMapper;

    @Test
    public void createAutor() throws Exception{
        AutorRequest request = new AutorRequest("testNume","testPrenume");
        when(autorService.saveAutor(any())).thenReturn(new Autor(1,"testNume","testPrenume"));
        mockMvc.perform(post("/autor")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nume").value(request.getNume()));
    }

    @Test
    public void getAllAutor() throws Exception {
        Autor autor1 = new Autor(1,"testNume","testPrenume");
        Autor autor2 = new Autor(2,"testNume2","testPrenume2");
        List<Autor> autorList = new ArrayList<>();
        autorList.add(autor1);
        autorList.add(autor2);

        when(autorService.getAllAutor()).thenReturn(autorList);
        mockMvc.perform(get("/autor")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(autorList.size()))
                //.andExpect(jsonPath("$[0]").value(autorList.get(0)))
                //.andExpect(jsonPath("$[1]").value(autorList.get(1)))
                .andExpect(jsonPath("$[0].nume").value("testNume"))
                .andExpect(jsonPath("$[1].nume").value("testNume2"));
    }

    @Test
    public void deleteAutor() throws Exception{
        Long id = 1L;
        autorService.deleteById(id);
        mockMvc.perform(delete("/autor/{id}",id)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
