package org.example.movieapi.service;

import org.example.movieapi.dto.MovieCreateDto;
import org.example.movieapi.dto.MovieDetailedDto;
import org.example.movieapi.dto.MovieSimpleDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<MovieSimpleDto> getMovies();
    Optional<MovieDetailedDto> getMovie(int movieId);
    MovieSimpleDto addMovie(MovieCreateDto movieDto);
}
