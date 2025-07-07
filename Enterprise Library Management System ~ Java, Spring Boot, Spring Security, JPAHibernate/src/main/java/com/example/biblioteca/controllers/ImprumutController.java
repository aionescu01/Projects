package com.example.biblioteca.controllers;


import com.example.biblioteca.dto.ImprumutRequest;
import com.example.biblioteca.dto.ImprumutUpdateDto;
import com.example.biblioteca.mapper.ImprumutMapper;

import com.example.biblioteca.model.Imprumut;
import com.example.biblioteca.services.ImprumutService;
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
@RequestMapping("/imprumut")
@Tag(name = "Imprumuturi",description = "Endpoint administrare Imprumuturi")
public class ImprumutController {

    ImprumutService imprumutService;
    ImprumutMapper imprumutMapper;

    public ImprumutController(ImprumutService imprumutService, ImprumutMapper imprumutMapper) {
        this.imprumutService = imprumutService;
        this.imprumutMapper = imprumutMapper;
    }

    @GetMapping(path = "/asc",produces = {MediaType.APPLICATION_JSON_VALUE })
    @Operation(description = "Afisarea imprumuturilor si a informatiilor despre acestea, ordonate alfabetic",
            summary = "Afisare Imprumuturi dupa ID",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(examples = {
                                    @ExampleObject(value = "{\"id\": 1,\"data_imprumut\": \"2024-01-12\",\"data_returnare\": \"2024-01-12\",\"carte\": {\"id\": 1},\"cititor\": {\"id\": 1}}")
                            }
                            )

                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(examples = {
                                    @ExampleObject()
                            }
                            )
                    ),
            })
    public ResponseEntity<List<Imprumut>> GetImprumutAscId(){
        return ResponseEntity.ok(imprumutService.getImprumutAscId());
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {MediaType.APPLICATION_JSON_VALUE })
    @Operation(description = "Creare imprumut - detalii necesare: data_imprumut, data_returnare [nu este obligatoriu], carte, cititor",
            summary = "Crearea unui nou imprumut",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = @Content(examples = {
                                    @ExampleObject(value = "{\"id\": 1,\"data_imprumut\": \"2024-01-12\",\"data_returnare\": \"2024-01-12\",\"carte\": {\"id\": 1},\"cititor\": {\"id\": 1}}")
                            }
                            )
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "500",
                            content = @Content(examples = {
                                    @ExampleObject()
                            }
                            )
                    ),
                    @ApiResponse(
                            description = "Field validation error",
                            responseCode = "400",
                            content = @Content(examples = {
                                    @ExampleObject(value = "{\"data_imprumut\": \"24-12-2022\",\"data_returnare\": \"12-12-2022\",\"carte\": {\"id\": 1},\"cititor\": {\"id\": 1}}")
                            }
                            )
                    ),
            })
    public ResponseEntity<Imprumut> createImprumut(@Valid
                                             @RequestBody
                                             @Parameter(description = "MyDto")
                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                     content = @Content(examples = {
                                                             @ExampleObject(value = "{\"data_imprumut\": \"2024-01-12\",\"data_returnare\": \"2024-01-12\",\"carte\": {\"id\": 1},\"cititor\": {\"id\": 1}}"),
                                                     }
                                                     ))
                                             ImprumutRequest imprumutRequest){
        Imprumut imprumut = imprumutService.saveImprumut(imprumutMapper.imprumutRequestToImprumut(imprumutRequest));
        return ResponseEntity.created(URI.create("/imprumut/" + imprumut.getId()))
                .body(imprumut);
    }


    @PutMapping(path = "/{id}",produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(description = "Updatarea imprumutului cu un id dat - se poate modifica sau adauga data de returnare",
            summary = "Updatarea unui imprumut cu un id dat",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(examples = {
                                    @ExampleObject(value = "{\"id\":1,\"data_imprumut\": \"2024-01-12\",\"data_returnare\": \"2024-01-12\",\"carte\": {\"id\": 1},\"cititor\": {\"id\": 1}}")
                            }
                            )
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(examples = {
                                    @ExampleObject()
                            }
                            )
                    ),
                    @ApiResponse(
                            description = "Field validation error",
                            responseCode = "400",
                            content = @Content(examples = {
                                    @ExampleObject(value = "{\"data_returnare\":\"22-03-2022\"}")
                            }
                            )
                    ),
            })
    public ResponseEntity<Imprumut> updateImprumut(@PathVariable
                                                       @Parameter(name = "id",description = "Codul imprumutului pe care doriti sa il modificati",example = "1",required = true)
                                                       Long id,
                                                       @Valid
                                                       @RequestBody
                                                       @Parameter(description = "MyDto")
                                                       @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                               content = @Content(examples = {
                                                                       @ExampleObject(value = "{\"data_returnare\":\"2022-03-22\"}")
                                                                       }
                                                               ))
                                                       ImprumutUpdateDto imprumutUpdateDto) throws Exception{
        return  ResponseEntity.ok(imprumutService.updateImprumut(id,imprumutUpdateDto));
    }

    @DeleteMapping(path = "/{id}")
    @Operation(description = "Stergerea imprumutului cu id dat",
            summary = "Stergerea unui imprumut cu id dat",
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
    public void deleteImprumut(@PathVariable
                                 @Parameter(name = "id",description = "Codul imprumutului pe care doriti sa il stergeti",example = "1",required = true)
                                 Long id)
    {
        imprumutService.deleteById(id);
    }

}
