package org.example.movieapi.service;

import org.example.movieapi.dto.MovieCreateDto;
import org.example.movieapi.dto.MovieDetailedDto;
import org.example.movieapi.dto.MovieSimpleDto;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    List<MovieSimpleDto> getMovieByTitle(String title);
    List<MovieSimpleDto> getMovieByYear(int year);
    List<MovieSimpleDto> getMovieByTitleAndYear(String title, int year);

    /**
     * persist movie
     * @param movieDto movie to persist
     * @return persisted movie with its id
     * @throws DataAccessException if persistence fails
     */
    MovieSimpleDto addMovie (MovieCreateDto movieDto);

    Optional<MovieDetailedDto> updateMovie(MovieSimpleDto movieSimpleDto);

    Optional<MovieDetailedDto> setDirector(int movieId, int directorId);

    Optional<MovieDetailedDto> setActors(int movieId, Set<Integer> actorIds);

    Optional<MovieDetailedDto> deleteMovie(int movieId);
}
