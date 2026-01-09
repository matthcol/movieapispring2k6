package org.example.movieapi.service.impl;

import org.example.movieapi.dto.MovieCreateDto;
import org.example.movieapi.dto.MovieDetailedDto;
import org.example.movieapi.dto.MovieSimpleDto;
import org.example.movieapi.entity.Movie;
import org.example.movieapi.entity.Person;
import org.example.movieapi.repository.MovieRepository;
import org.example.movieapi.repository.PersonRepository;
import org.example.movieapi.service.MovieService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional //Permet d'annuler tout le block si on a plusieurs requêtes SQL si il y a une erreur sur une seule.
@Profile("default") //Ce service ne sera activé que dans le profil "defaut"
// Ca permet de lancer l'application même si on a 2 Services --> On met celui-ci en "default"
public class MovieServiceJpa implements MovieService {
//TODO: Faire les tests unitaires --> Comme pour la partie "MovieService" et on va Mock le composant sous-jacent (= Repository)

    //On va définir un attribut de ce composant:
    @Autowired //DI = Injection de dépendance
    private MovieRepository movieRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ModelMapper modelMapper;

    private Logger logger = LoggerFactory.getLogger(MovieServiceJpa.class);

    @Override
    public List<MovieSimpleDto> getMovies() {
        return movieRepository.findAll()
                .parallelStream()
                .map( movieEntity -> modelMapper.map(
                        movieEntity, MovieSimpleDto.class
                ))
                .toList();
    }

    @Override
    public Optional<MovieDetailedDto> getMovie(int movieId) {
        return movieRepository.findById(movieId)
                .map( movieEntity -> modelMapper.map(
                        movieEntity, MovieDetailedDto.class
                ));
    }

    @Override
    public List<MovieSimpleDto> getMovieByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title)
                .parallelStream()
                .map(movieEntity -> modelMapper.map(
                        movieEntity, MovieSimpleDto.class
                ))
                .toList();
    }

    @Override
    public List<MovieSimpleDto> getMovieByYear(int year) {
        return movieRepository.findByReleaseYear(year)
                .parallelStream()
                .map(movieEntity -> modelMapper.map(
                        movieEntity, MovieSimpleDto.class
                ))
                .toList();
    }

    @Override
    public List<MovieSimpleDto> getMovieByTitleAndYear(String title, int year) {
        var patternTitle = '%' + title.toUpperCase() + '%';
        return movieRepository.findByTitleYear(patternTitle, year)
                .parallelStream()
                .map(movieEntity -> modelMapper.map(
                        movieEntity, MovieSimpleDto.class
                ))
                .toList();
    }

    @Override
    public MovieSimpleDto addMovie(MovieCreateDto movieDto) {
        var movieEntity = modelMapper.map(movieDto, Movie.class);
        movieRepository.saveAndFlush(movieEntity);
        logger.debug("Movie saved: {}", movieEntity);
        return modelMapper.map(movieEntity, MovieSimpleDto.class);
    }

    @Override
    public Optional<MovieDetailedDto> updateMovie(MovieSimpleDto movieSimpleDto) {
        return movieRepository.findById(movieSimpleDto.getMovieId())
                .map(movieEntity -> {
                    modelMapper.map(movieSimpleDto, movieEntity);
                    movieRepository.flush();
                    return modelMapper.map(movieEntity, MovieDetailedDto.class);
                });
    }

    @Override
    public Optional<MovieDetailedDto> setDirector(int movieId, int directorId) {
        return movieRepository.findById(movieId)
                .flatMap( movieEntity -> personRepository.findById(directorId)
                        .map(directorEntity -> {
                            movieEntity.setDirector(directorEntity);
                            movieRepository.flush();
                            return modelMapper.map(movieEntity, MovieDetailedDto.class);
                        })
                );
    }

    @Override
    public Optional<MovieDetailedDto> setActors(int movieId, Set<Integer> actorIds) {
        return movieRepository.findById(movieId)
                .flatMap(movieEntity -> {
                    var listActorEntity = personRepository.findAllById(actorIds);
                    if (listActorEntity.size() != actorIds.size()){
                        return Optional.empty();
                    }
                    //On a trouvé tous les acteurs
                    movieEntity.setActors(new HashSet<>(listActorEntity));
                    movieRepository.flush();
                    return Optional.of(modelMapper.map(movieEntity, MovieDetailedDto.class));
                    //On fait un "optional.of" car on a un "optional" qui est retourné dans le "if" donc on doit avoir le
                    //"même type de donnée" dans les 2 return
                });
    }

    @Override
    public Optional<MovieDetailedDto> deleteMovie(int movieId) {
        return movieRepository.findById(movieId)
                .map(movieEntity -> {
                    var movieDetailDto = modelMapper.map(movieEntity, MovieDetailedDto.class);
                    movieRepository.deleteById(movieId);
                    movieRepository.flush();
                    return movieDetailDto;
                });
    }
}
