package org.example.movieapi.repository.demo;

import jakarta.persistence.EntityManager;
import org.example.movieapi.entity.Movie;
import org.example.movieapi.entity.Person;
import org.example.movieapi.repository.MovieRepository;
import org.example.movieapi.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.stream.Stream;

@DataJpaTest //Met en place la partie JPA
class MovieRepositoryCrudDemo {
    @Autowired //DI --> Injection de dépendances
    MovieRepository movieRepository;

    @Autowired
    PersonRepository personRepository;

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

    //@Rollback(value = false)
    @Test
    void demoCrudDirector() {
        Movie movie = Movie.builder()
                .title("Avatar: Fire and Ash")
                .releaseYear(2025)
                //.duration(197)
                .build();
        Person director = Person.builder()
                .name("James Cameron")
                .birthdate(LocalDate.of(1964, 8, 16))
                .build();

        Stream.of(movie,director)
                        .forEach(System.out::println);
        //System.out.println(movie);
        movieRepository.saveAndFlush(movie);
        personRepository.saveAndFlush(director);
        System.out.println("###### Movie Saved: ");
        //System.out.println(movie);
        Stream.of(movie,director)
                .forEach(System.out::println);
        int movieId = movie.getMovieId();
        int directorId = director.getPersonId();

        //Vider le cache hibernate:
        entityManager.clear();

        //Lire la data:
        var optMovie2 = movieRepository.findById(movieId);
        var optDirector = personRepository.findById(directorId);
        System.out.println("###### Movie + Director read after emptying cache: ");
        //System.out.println(optMovie2);
        Assertions.assertTrue(optMovie2.isPresent());// On test que "optMovie2" n'est pas null
        Assertions.assertTrue(optDirector.isPresent());
        var movieRead = optMovie2.get();
        var directorRead = optDirector.get();
        Stream.of(movieRead,directorRead)
                .forEach(System.out::println);

        //Association:
        movieRead.setDirector(directorRead);
        movieRepository.flush();
    }
}