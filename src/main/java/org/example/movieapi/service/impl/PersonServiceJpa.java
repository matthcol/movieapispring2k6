package org.example.movieapi.service.impl;

import org.example.movieapi.dto.PersonCreateDto;
import org.example.movieapi.dto.PersonSimpleDto;
import org.example.movieapi.entity.Person;
import org.example.movieapi.repository.PersonRepository;
import org.example.movieapi.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Profile("default")
public class PersonServiceJpa implements PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PersonSimpleDto addPerson(PersonCreateDto personCreateDto) {
        var personEntity = modelMapper.map(personCreateDto, Person.class);
        personRepository.saveAndFlush(personEntity);
        return modelMapper.map(personEntity, PersonSimpleDto.class);
    }
}
