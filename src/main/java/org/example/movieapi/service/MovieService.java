package org.example.movieapi.service;

import org.example.movieapi.dto.MovieCreateDto;
import org.example.movieapi.dto.MovieDetailedDto;
import org.example.movieapi.dto.MovieSimpleDto;
import org.springframework.dao.DataAccessException;

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
     * persist movie
     * @param movieDto movie to persist
     * @return persisted movie with its id
     * @throws DataAccessException if persistence fails
     */
    MovieSimpleDto addMovie (MovieCreateDto movieDto);
}
