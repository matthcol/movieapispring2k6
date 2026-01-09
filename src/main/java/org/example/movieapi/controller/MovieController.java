package org.example.movieapi.controller;

import jakarta.validation.Valid;
import org.example.movieapi.dto.MovieCreateDto;
import org.example.movieapi.dto.MovieDetailedDto;
import org.example.movieapi.dto.MovieSimpleDto;
import org.example.movieapi.exception.NotFoundException;
import org.example.movieapi.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@RestController //On a crée un nouveau type de composant découvrable automatiquement
@RequestMapping("/api/movie") //Tuning --> personnalisation de la route
public class MovieController {

    @Autowired
    private MovieService movieService;

    private Logger logger = LoggerFactory.getLogger(MovieController.class);

//    @GetMapping
//    public List<Movie> getMovies(){
//        return List.of(
//                Movie.builder()
//                        .title("Avatar")
//                        .build(),
//                Movie.builder()
//                        .title("Harry Potter")
//                        .build()
//        );
//    }

    @GetMapping
    public List<MovieSimpleDto> getMovies(){
        return movieService.getMovies();
    }

    @GetMapping("/{movieId}")
    public MovieDetailedDto getMovie(@PathVariable("movieId") int movieId){
        return movieService.getMovie(movieId)
//                .orElseThrow(
//                        () -> new ResponseStatusException(
//                                HttpStatus.NOT_FOUND,
//                                MessageFormat.format("Movie {0} not found", movieId)
//                        )
//                );
                .orElseThrow(
                        () -> new NotFoundException("movie",movieId)
                );
    }

    // URL examples:
    //              * /api/movie/search?t=star&y=1977
    //              * /api/movie/search?t=star
    //              * /api/movie/search?y=1977
    @GetMapping("/search")
    public List<MovieSimpleDto> searchMovie(
            @RequestParam(required = false, name = "t") String titlePart,
            @RequestParam(required = false, name = "y") Integer year
    ){
        if (Objects.nonNull(titlePart) && Objects.nonNull(year)){
            return movieService.getMovieByTitleAndYear(titlePart, year);
        } else if (Objects.nonNull(titlePart)) {
            return movieService.getMovieByTitle(titlePart);
        } else if (Objects.nonNull(year)) {
            return movieService.getMovieByYear(year);
        } else {
            logger.error("Search movie without criteria");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No search criteria provided");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieSimpleDto addMovie(@RequestBody @Valid MovieCreateDto movieDto){
        //On utlise le "@Valid" pour prendre en compte les annotations des min, max, notNull, ...
        logger.debug("Movie to save: {}", movieDto);
        var result = movieService.addMovie(movieDto);
        logger.info("Movie {} saved with id {}", result.getTitle(), result.getMovieId());
        return result;
    }

    @PutMapping
    public MovieDetailedDto updateMovie(@RequestBody MovieSimpleDto movieSimpleDto){

        return movieService.updateMovie(movieSimpleDto)
                .orElseThrow( () -> new NotFoundException("movie", movieSimpleDto.getMovieId()));
    }

    @PatchMapping("/{movieId}/director/{directorId}")
    public MovieDetailedDto setDirector(@PathVariable int movieId, @PathVariable int directorId){
        return movieService.setDirector(movieId, directorId)
                .orElseThrow(() -> new NotFoundException("movie or person", movieId) );
        //TODO: Prévoir plusieurs ID et plusieurs types d'entités
    }

    @PatchMapping("/{movieId}/actors")
    public MovieDetailedDto setActors(@PathVariable int movieId, @RequestBody Set<Integer> actorIds){
        return movieService.setActors(movieId, actorIds)
                .orElseThrow( () -> new NotFoundException("movie or actors", movieId));
        //TODO:  Prévoir plusieurs ID et plusieurs types d'entités
    }

    @DeleteMapping("/{movieId}")
    public MovieDetailedDto deleteMovie(@PathVariable int movieId){

        return movieService.deleteMovie(movieId)
                .orElseThrow( () -> new NotFoundException("movie", movieId));
    }
}
