package org.example.movieapi.controller;

import jakarta.validation.Valid;
import org.example.movieapi.dto.PersonCreateDto;
import org.example.movieapi.dto.PersonSimpleDto;
import org.example.movieapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonSimpleDto addPerson(@RequestBody @Valid PersonCreateDto personCreateDto){
        return personService.addPerson(personCreateDto);
    }
}
