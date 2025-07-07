package com.example.biblioteca.controllers;


import com.example.biblioteca.dto.AutorRequest;
import com.example.biblioteca.mapper.AutorMapper;
import com.example.biblioteca.model.Autor;
import com.example.biblioteca.services.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController()
@Validated
@RequestMapping("/autor")
@Tag(name = "Autori",description = "Endpoint administrare Autori")
public class AutorController {

    AutorService autorService;
    AutorMapper autorMapper;

    public AutorController(AutorService autorService, AutorMapper autorMapper) {
        this.autorService = autorService;
        this.autorMapper = autorMapper;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE })
    @Operation(description = "Afisarea autorilor si a informatiilor legate de acestia",
            summary = "Afisare Autori",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(examples = {
                                    @ExampleObject(value = "[{\"id\":\"1\",\"nume\":\"Caragiale\",\"prenume\":\"I. L.\"}]"),
                            })
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(examples = {
                                    @ExampleObject(),
                            })
                    ),
            })
    public ResponseEntity<List<Autor>> GetAutori(){
        return ResponseEntity.ok(autorService.getAllAutor());
    }

    @GetMapping(path = "/asc",produces = {MediaType.APPLICATION_JSON_VALUE })
    @Operation(description = "Afisarea autorilor si a informatiilor despre acestia, in ordine alfabetica",
            summary = "Afisare Autori alfabetic",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(examples = {
                                    @ExampleObject(value = "[{\"id\":\"1\",\"nume\":\"Caragiale\",\"prenume\":\"I. L.\"}]"),
                            })
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(examples = {
                                    @ExampleObject(),
                            })
                    ),
            })
    public ResponseEntity<List<Autor>> GetAutorAscNume(){
        return ResponseEntity.ok(autorService.getAutorAscNume());
    }



    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {MediaType.APPLICATION_JSON_VALUE })
    @Operation(description = "Creare autor - detalii necesare: nume, prenume",
            summary = "Crearea unui nou autor",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = @Content(examples = {
                                    @ExampleObject(value = "{\"id\":\"1\",\"nume\":\"Caragiale\",\"prenume\":\"I. L.\"}"),
                            })
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "500",
                            content = @Content(examples = {
                                    @ExampleObject(),
                            })
                    ),
                    @ApiResponse(
                            description = "Field validation error",
                            responseCode = "400",
                            content = @Content(examples = {
                                    @ExampleObject(value = "{\"id\":\"1\",\"nume\":\"tes1t\",\"prenume\":\"te_st\"}"),
                            })
                    ),

            })
    public ResponseEntity<Autor> createAutor(@Valid
                                             @RequestBody
                                             @Parameter(description = "MyDto")
                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                     content = @Content(examples = {
                                                             @ExampleObject(value = "{\"nume\":\"test\",\"prenume\":\"test\"}"),

                                                     }
                                                     ))
                                             AutorRequest autorRequest){
        Autor autor = autorService.saveAutor(autorMapper.autorRequestToAutor(autorRequest));
        return ResponseEntity.created(URI.create("/antrenor/" + autor.getId()))
                .body(autor);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(description = "Stergerea autorului cu id dat",
            summary = "Stergerea unui autor cu id dat",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404"
                    ),
            })
    public void deleteAutor(@PathVariable
                               @Parameter(name = "id",description = "Codul autorului pe care doriti sa il stergeti",example = "1",required = true)
                               Long id)
    {
        autorService.deleteById(id);
    }

}
