package org.example.movieapi.service.impl;

import org.example.movieapi.dto.MovieCreateDto;
import org.example.movieapi.dto.MovieDetailedDto;
import org.example.movieapi.dto.MovieSimpleDto;
import org.example.movieapi.repository.MovieRepository;
import org.example.movieapi.service.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("default") //Ce service ne sera activé que dans le profil "defaut"
// Ca permet de lancer l'application même si on a 2 Services --> On met celui-ci en "default"
public class MovieServiceJpa implements MovieService {
//TODO: Faire les tests unitaires --> Comme pour la partie "MovieService" et on va Mock le composant sous-jacent (= Repository)

    //On va définir un attribut de ce composant:
    @Autowired //DI = Injection de dépendance
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MovieSimpleDto> getMovies() {

        return movieRepository.findAll()
                .parallelStream()
                .map( movieEntity -> modelMapper.map(
                        movieEntity, MovieSimpleDto.class
                ))
                .toList();
    }

    @Override
    public Optional<MovieDetailedDto> getMovie(int movieId) {
        return Optional.empty();
    }

    @Override
    public List<MovieSimpleDto> getMovieByTitle(String title) {
        return List.of();
    }

    @Override
    public List<MovieSimpleDto> getMovieByYear(int year) {
        return List.of();
    }

    @Override
    public List<MovieSimpleDto> getMovieByTitleAndYear(String title, int year) {
        return List.of();
    }

    @Override
    public MovieSimpleDto addMovie(MovieCreateDto movieDto) {
        return null;
    }

    @Override
    public Optional<MovieDetailedDto> updateMovie(MovieSimpleDto movieSimpleDto) {
        return Optional.empty();
    }

    @Override
    public Optional<MovieDetailedDto> setDirector(int movieId, int directorId) {
        return Optional.empty();
    }

    @Override
    public Optional<MovieDetailedDto> setActors(int movieId, Set<Integer> actorIds) {
        return Optional.empty();
    }

    @Override
    public Optional<MovieDetailedDto> deleteMovie(int movieId) {
        return Optional.empty();
    }
}
