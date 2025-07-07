package com.example.biblioteca.controllers;


import com.example.biblioteca.dto.CategorieCarteRequest;
import com.example.biblioteca.mapper.CategorieCarteMapper;
import com.example.biblioteca.model.CategorieCarte;
import com.example.biblioteca.services.CategorieCarteService;
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
@RequestMapping("/categorie_carte")
@Tag(name = "Categorii Carti",description = "Endpoint administrare Categorii Carti")
public class CategorieCarteController {

    CategorieCarteService categorieCarteService;
    CategorieCarteMapper categorieCarteMapper;

    public CategorieCarteController(CategorieCarteService categorieCarteService, CategorieCarteMapper categorieCarteMapper) {
        this.categorieCarteService = categorieCarteService;
        this.categorieCarteMapper = categorieCarteMapper;
    }

    @GetMapping(path = "/asc",produces = {MediaType.APPLICATION_JSON_VALUE })
    @Operation(description = "Afisarea categoriilor cartilor si a informatiilor despre acestea, ordonate alfabetic",
            summary = "Afisare Categorii Carti alfabetic",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(examples = {
                                    @ExampleObject(value = "{\"id\":1,\"nume\":\"Memorii\"}"),
                            }
                            )
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(examples = {
                                    @ExampleObject(),
                            }
                            )
                    ),
            })
    public ResponseEntity<List<CategorieCarte>> GetCategorieCarteAscNume(){
        return ResponseEntity.ok(categorieCarteService.getCategorieCarteAscNume());
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {MediaType.APPLICATION_JSON_VALUE })
    @Operation(description = "Creare categorie carte - detalii necesare: nume categorie",
            summary = "Crearea unei noi categorii de carte",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = @Content(examples = {
                                    @ExampleObject(value = "{\"id\":1,\"nume\":\"Memorii\"}"),
                            }
                            )
                    ),
                    @ApiResponse(
                            description = "Bad Request - validation error per request",
                            responseCode = "500",
                            content = @Content(examples = {
                                    @ExampleObject(),
                            }
                            )
                    ),
                    @ApiResponse(
                            description = "Field validation error",
                            responseCode = "400",
                            content = @Content(examples = {
                                    @ExampleObject(value = "{\"id\":1,\"nume\":\"Memo1ri_i\"}"),
                            }
                            )
                    ),
            })
    public ResponseEntity<CategorieCarte> createCategorieCarte(@Valid
                                             @RequestBody
                                             @Parameter(description = "MyDto")
                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                     content = @Content(examples = {
                                                             @ExampleObject(value = "{\"nume\":\"test\"}"),
                                                     }
                                                     ))
                                                      CategorieCarteRequest categorieCarteRequest){
        CategorieCarte categorieCarte = categorieCarteService.saveCategorieCarte(categorieCarteMapper.categorieCarteRequestToCategorieCarte(categorieCarteRequest));
        return ResponseEntity.created(URI.create("/categorie_carte/" + categorieCarte.getId()))
                .body(categorieCarte);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(description = "Stergerea categoriei cu id dat",
            summary = "Stergerea unei categorii cu id dat",
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
    public void deleteCategorieCarte(@PathVariable
                               @Parameter(name = "id",description = "Codul categoriei pe care doriti sa il stergeti",example = "1",required = true)
                               Long id)
    {
        categorieCarteService.deleteById(id);
    }
}
