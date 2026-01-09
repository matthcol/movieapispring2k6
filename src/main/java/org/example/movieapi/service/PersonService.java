package org.example.movieapi.service;

import org.example.movieapi.dto.PersonCreateDto;
import org.example.movieapi.dto.PersonSimpleDto;

public interface PersonService {
    PersonSimpleDto addPerson(PersonCreateDto personCreateDto);
}
