package org.example.movieapi.repository.demo;

import jakarta.persistence.EntityManager;
import org.example.movieapi.entity.Movie;
import org.example.movieapi.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest //Met en place la partie JPA
class MovieRepositoryCrudDemo {
    @Autowired //DI --> Injection de dépendances
    MovieRepository movieRepository;

    @Autowired
    EntityManager entityManager; // Cache hibernate

    @Test
    void demoCrud(){
        Movie movie = Movie.builder()
                .title("Avatar: Fire and Ash")
                .releaseYear(2025)
                //.duration(197)
                .build();
        System.out.println(movie);
        movieRepository.saveAndFlush(movie);
        System.out.println("######");
        System.out.println(movie);
        System.out.println("######");
        int movieId = movie.getMovieId();

        //Vider le cache hibernate:
        entityManager.clear();

        //Lire la data:
        var optMovie2 = movieRepository.findById(movieId);
        System.out.println(optMovie2);
        Assertions.assertTrue(optMovie2.isPresent());// On test que "optMovie2" n'est pas null
        var movie2 = optMovie2.get();
        System.out.println("###### Avant Update:");

        //Update:
        System.out.println(movie2);
        movie2.setDuration(197);
        movieRepository.flush();//Pour synchroniser les modifs avec la DB
        System.out.println("###### Après Update:");
        System.out.println(movie2);

        //Delete:
        movieRepository.delete(movie2);
        movieRepository.flush();
    }
}