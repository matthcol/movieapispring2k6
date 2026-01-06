package org.example.movieapi.service;

import org.example.movieapi.dto.MovieCreateDto;
import org.example.movieapi.dto.MovieDetailedDto;
import org.example.movieapi.dto.MovieSimpleDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    /**
     *
     * @return
     */
    List<MovieSimpleDto> getMovies();

    /**
     *
     * @param movieId
     * @return
     */
    Optional<MovieDetailedDto> getMovie(int movieId);

    /**
     *
     * @param movieDto
     * @return
     */
    MovieSimpleDto addMovie(MovieCreateDto movieDto);
}
