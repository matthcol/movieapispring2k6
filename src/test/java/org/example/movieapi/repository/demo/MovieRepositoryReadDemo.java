package org.example.movieapi.repository.demo;

import org.example.movieapi.entity.Movie;
import org.example.movieapi.repository.MovieRepository;
import org.example.movieapi.repository.PersonRepository;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.jdbc.Sql;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DataJpaTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class MovieRepositoryReadDemo {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    PersonRepository personRepository;

    @Test
    void demoFindAll(){
        movieRepository.findAll()
                .forEach(System.out::println);
    }

    @ParameterizedTest
    @ValueSource(ints={
            1,2,5,9,6,10,0
    })
    void demoFindByIdIf(int movieId){
        var optMovie = movieRepository.findById(movieId);
        System.out.println(optMovie);
        System.out.println("###########");
        if (optMovie.isPresent()){
            var movie = optMovie.get();
            System.out.println(MessageFormat.format("Le Movie avec ID {0} est: {1}", movieId, movie));
        } else {
            System.out.println(MessageFormat.format("Pas de Movie avec ID {0}", movieId));
        }
    }

    @ParameterizedTest
    @ValueSource(ints={
            1,2,5,9,6,10,0
    })
    void demoFindByIdIfPresent(int movieId){
        var optMovie = movieRepository.findById(movieId);
        System.out.println(optMovie);
        System.out.println("###########");
        optMovie.ifPresent(movie -> System.out.println(
                MessageFormat.format("Le Movie avec ID {0} est: {1} ({2})", movieId, movie.getTitle(), movie.getReleaseYear())
        ));
    }

    public static Stream<Arguments> sortSource() {
        return Stream.of(
                Arguments.of(
                        Named.of(
                                "releaseYear, title",
                                Sort.by("releaseYear","title")
                        )
                ),
                Arguments.of(
                        Named.of(
                                "releaseYear desc, title",
                                Sort.by(
                                        Sort.Order.desc("releaseYear"),
                                        Sort.Order.asc("title")
                                )
                        )
                )
        );
    }

    @ParameterizedTest(name = "By {0}")
    @MethodSource("sortSource")
    void demoFindAllSorted(Sort sort){
        movieRepository.findAll(sort)
                .forEach(System.out::println);
    }

    @Test
    void demoFindAllPagination(){
        Pageable pageable = Pageable.ofSize(4);
        var moviePage = movieRepository.findAll(pageable);
        System.out.println("Nbre Total de page: " + moviePage.getTotalPages());
        System.out.println("Nbre Total de éléments: " + moviePage.getTotalElements());
        System.out.println("Page 1:");
        moviePage.get().forEach(System.out::println);

        //Nouvelle page:
        var pageableNext = PageRequest.of(1,4);
        var moviePage2 = movieRepository.findAll(pageableNext);
        System.out.println("Page 2:");
        moviePage2.get().forEach(System.out::println);
    }

    public static Stream<Arguments> findWithExample() {
        var movieProbe1 = Movie.builder().releaseYear(2025).build();
        var movieProbe2 = Movie.builder().duration(129).build();
        var matcher2 = ExampleMatcher.matching().withIgnorePaths("releaseYear");

        var movieProbe3 = Movie.builder().title("superman").releaseYear(2025).build();
        var matcher3 = ExampleMatcher.matching()
                .withMatcher("title", match -> match.ignoreCase());//On peut faire "ALT + Entrée" pour remplacer par une référence

        var movieProbe4 = Movie.builder().title("superman").build();
        var matcher4 = ExampleMatcher.matching()
                .withIgnorePaths("releaseYear")
                .withMatcher("title", match -> match
                        .ignoreCase()
                        .startsWith()
                );

        return Stream.of(
                arguments(named("Year 2025",Example.of(movieProbe1))),
                arguments(named("Duration 129",Example.of(movieProbe2, matcher2))),
                //Titre en ignorant la casse (Superman) de l'année 2025
                //Titre commencant par "Superman" tjrs en ignorant la casse
                arguments(named("Titre en ignorant la casse en 2025",Example.of(movieProbe3, matcher3))),
                arguments(named("Titre en ignorant la casse et qui commence par \"Superman\"",Example.of(movieProbe4, matcher4)))
        );
    }

    @ParameterizedTest
    @MethodSource("findWithExample")
    void demoFindAllWithExample(Example<Movie> example){
        //QBE = Query By Example --> C'est un pattern
        movieRepository.findAll(example).forEach(System.out::println);
    }

    @Test
    void demoFindByReleaseYearBetweenOrderByReleaseYear(){
        movieRepository.findByReleaseYearBetweenOrderByReleaseYear(2000, 2025)
                .forEach(System.out::println);
    }

    @Test
    void demoFindByTitleContainingIgnoreCaseAndReleaseYearGreaterThan(){
        movieRepository.findByTitleContainingIgnoreCaseAndReleaseYearGreaterThan("Harry",1900, Sort.by("releaseYear"))
                .forEach(System.out::println);
    }

    @Test
    void demoFindByTitleYear(){
        movieRepository.findByTitleYear("%HARRY%", 2002)
                .forEach(System.out::println);
    }

    @Test
    void demoFindByYearDuration(){
        movieRepository.findByYearDuration(1999, 100)
                .forEach(System.out::println);
    }

    @Test
    void demoFindByDirector(){
        movieRepository.findByDirectorNameContaining("James Cameron")
                .forEach(System.out::println);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Cameron", "James", "James Cameron",  // OK
            "james cameron"  // KO
    })
    void demoFindByDirectorName(String name){
        movieRepository.findByDirectorName(name, Sort.by("director.personId","releaseYear"))
                .forEach(movie -> System.out.println(MessageFormat.format(
                        "Film de {0,number,#} - {1} réalisé par {2}",
                        movie.getReleaseYear(),
                        movie.getTitle(),
                        movie.getDirector().getName()
                )));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "% Cameron", "James %", "James Cameron",  "james cameron"
    })
    void demoFindByDirectorNameCI(String namePattern){
        String namePatternNormalize = namePattern.toLowerCase();
        movieRepository.findByDirectorNameCI(namePatternNormalize, Sort.by("director.personId","releaseYear"))
                .forEach(movie -> System.out.println(MessageFormat.format(
                        "Film de {0,number,#} - {1} réalisé par {2}",
                        movie.getReleaseYear(),
                        movie.getTitle(),
                        movie.getDirector().getName()
                )));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Sam", "Daniel", "Radcliffe", "Corenswet"
    })
    void demoFindByActorsName(String nameActor){
        movieRepository.findByActorName(nameActor, Sort.by(
                "releaseYear"))
                .forEach(movie -> System.out.println(
                        MessageFormat.format(
                                "Acteur {0} a joué dans: {1} ({2,number,#})",
                                nameActor,
                                movie.getTitle(),
                                movie.getReleaseYear()
                        )
                ));
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,6,9,10,0})
    void demoFindByActorId(int actorId){
        personRepository.findById(actorId)
                .ifPresentOrElse(
                        person -> movieRepository.findByActorId(person.getPersonId())
                                .forEach( movie -> System.out.println(MessageFormat.format(
                                        "Acteur {0} avec ID {1} à joué dans le film {2} ({3})",
                                        person.getName(),
                                        actorId,
                                        movie.getTitle(),
                                        movie.getReleaseYear()
                                ))),
                        () -> System.out.println("Aucun acteur n'a été trouvé avec l'ID: "+actorId)
                );
    }

    @Test
    void demoFindByIdEntityGraph(){
        int movieId = 1;
        movieRepository.findById(movieId)
                .ifPresent(
                        movie -> {
                            System.out.println(MessageFormat.format(
                                    "Le film avec ID {0} est: {1} ({2})",
                                    movieId,
                                    movie.getTitle(),
                                    movie.getReleaseYear()
                            ));
                            System.out.println("Le réalisateur est: " + (
                                    Objects.nonNull(movie.getDirector())
                                    ? movie.getDirector().getName()
                                            : "Inconnu"));
                            System.out.println("Les acteurs sont: ");
                            movie.getActors().forEach(
                                    actor -> System.out.println("     - " + actor.getName())
                            );
                        }
                );
    }
}
