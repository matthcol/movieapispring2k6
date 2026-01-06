package org.example.movieapi.controller;

import org.example.movieapi.dto.MovieCreateDto;
import org.example.movieapi.dto.MovieDetailedDto;
import org.example.movieapi.dto.MovieSimpleDto;
import org.example.movieapi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@RestController //On a crée un nouveau type de composant découvrable automatiquement
@RequestMapping("/api/movie") //Tuning --> personnalisation de la route
public class MovieController {

    @Autowired
    private MovieService movieService;

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
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                MessageFormat.format("Movie {0} not found", movieId)
                        )
                        //TODO: Améliorer par rapport aux divers status (400/500)
                );
    }

    @PostMapping
    public MovieSimpleDto addMovie(@RequestBody MovieCreateDto movieDto){
        return movieService.addMovie(movieDto);
    }

}
