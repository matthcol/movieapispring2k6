package org.example.movieapi.service.impl;

import org.example.movieapi.dto.MovieCreateDto;
import org.example.movieapi.dto.MovieDetailedDto;
import org.example.movieapi.dto.MovieSimpleDto;
import org.example.movieapi.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FakeMovieService implements MovieService {
    @Override
    public List<MovieSimpleDto> getMovies() {
        return List.of(
                MovieSimpleDto.builder()
                        .title("Avatar")
                        .build(),
                MovieSimpleDto.builder()
                        .title("Harry Potter")
                        .build(),
                MovieSimpleDto.builder()
                        .title("One Battle After Another")
                        .build()
        );
    }

    @Override
    public Optional<MovieDetailedDto> getMovie(int movieId) {
        return Optional.of(
               MovieDetailedDto.builder()
                       .movieId(movieId)
                       .title("Fast And Furious")
                       .build()
        );
    }

    @Override
    public MovieSimpleDto addMovie(MovieCreateDto movieDto) {
        return MovieSimpleDto.builder()
                .movieId(5)
                .title(movieDto.getTitle())
                .releaseYear(movieDto.getReleaseYear())
                .duration(movieDto.getDuration())
                .genres(movieDto.getGenres())
                .build();
    }
}
