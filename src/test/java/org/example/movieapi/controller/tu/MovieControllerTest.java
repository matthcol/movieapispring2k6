package org.example.movieapi.controller.tu;

import org.example.movieapi.controller.MovieController;
import org.example.movieapi.controller.tu.fixture.JsonProvider;
import org.example.movieapi.dto.MovieDetailedDto;
import org.example.movieapi.dto.MovieSimpleDto;
import org.example.movieapi.dto.PersonSimpleDto;
import org.example.movieapi.service.MovieService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest //Met en place que la partie controller sans mettre en place la partie data
class MovieControllerTest {

    //@Autowired
    //MovieController movieController; //Dépend de "MovieService" --> On va passer par une technique de "mock" (fournir un faux MovieService)

    @Autowired
    MockMvc mockMvc; //Le wrapper servlet des différents controllers

    @MockitoBean
    MovieService movieService;

    final static String BASE_URI = "/api/movie";

    @Test
    void testGetMovie_whenAbsent() throws Exception {

        //Préparer les hypothèses:
        int movieId = 5;

        //Préparer le composant Mock:
        Mockito.when(movieService.getMovie(movieId))
                .thenReturn(Optional.empty())
        ;

        //Appeler le controller via le composant "mockMvc":
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URI + "/{movieId}", movieId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;

    }

    @Test
    void testGetMovie_whenPresent() throws Exception {

        //Préparer les hypothèses:
        int movieId = 5;
        String title = "Avatar 1";
        int releaseYear = 2018;
        int duration = 160;

        String directorName = "James Cameron";

        PersonSimpleDto director = PersonSimpleDto.builder()
                .name(directorName)
                .build();

        PersonSimpleDto actor1 = PersonSimpleDto.builder()
                        .name("Zoé Saldaña")
                        .build();

        PersonSimpleDto actor2 = PersonSimpleDto.builder()
                .name("Sam Worthington")
                .build();

        MovieDetailedDto movieDetail = MovieDetailedDto.builder()
                .movieId(movieId)
                .title(title)
                .releaseYear(releaseYear)
                .duration(duration)
                .director(director)
                .actor(actor1)
                .actor(actor2)
                .build();

        //Préparer le composant Mock:
        Mockito.when(movieService.getMovie(movieId))
                .thenReturn(Optional.of(movieDetail));

        //Appeler le controller via le composant "mockMvc":
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URI + "/{movieId}", movieId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.movieId").value(movieId),
                        //$ désigne l'objet qu'on reçoit
                        MockMvcResultMatchers.jsonPath("$.title").value(title),
                        MockMvcResultMatchers.jsonPath("$.releaseYear").value(releaseYear),
                        MockMvcResultMatchers.jsonPath("$.duration").value(duration),
                        MockMvcResultMatchers.jsonPath("$.director.name").value(directorName),
                        MockMvcResultMatchers.jsonPath("$.actors", Matchers.hasSize(2))
                )
        ;
        //On va vérifier que le MockService a bien été appelé:
        BDDMockito.then(movieService)
                .should()
                .getMovie(movieId)
        ;
    }

    //Le Add correspond aux requêtes "POST"
    @ParameterizedTest
    @CsvSource(
            quoteCharacter = '|',
            value = {
                    "Avatar 1,2018,180,|action,sci-fi,adventure|",
                    "Avatar 2,2023,183,",
                    "Avatar 3,2025,,|action,sci-fi,adventure|",
                    "|Night of the Day of the Dawn of the Son of the Bride of the Return of the Revenge of the Terror of the Attack of the Evil Mutant Hellbound Flesh Eating Crawling Alien Zombified Subhumanoid Living Dead, Part 5|,2011,,",
                    "Z,1969,,"
            }
    )
    void testAddMovie_whenValid(String title, int releaseYear, Integer duration, String genres) throws Exception {

        //Préparer un JSON à envoyer à partir des hypothèses:
        String movieJsonToSend = JsonProvider.movieJSON(title, releaseYear, duration, genres);
        System.out.println(movieJsonToSend);

        //Préparer la réponse du (Mock)Service:
        int movieId = 50;

        MovieSimpleDto movieSimpleDto = MovieSimpleDto.builder()
                .movieId(movieId)
                .title(title)
                .releaseYear(releaseYear)
                .duration(duration)
                //TODO: Rajouter les genres
                .build();

        BDDMockito.given(movieService.addMovie(ArgumentMatchers.any()))
                .willReturn(movieSimpleDto);

        //Appel du controller:
        mockMvc.perform(post(BASE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieJsonToSend)
        )
                .andDo(print()) //ALT entrée sur le print de "MockMvcResultHandlers.print()" pour avoir cet affichage

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.movieId").value(movieId),
                        //$ désigne l'objet qu'on reçoit
                        MockMvcResultMatchers.jsonPath("$.title").value(title),
                        MockMvcResultMatchers.jsonPath("$.releaseYear").value(releaseYear),
                        MockMvcResultMatchers.jsonPath("$.duration").value(duration))
        ;

        BDDMockito.then(movieService)
                .should()
                .addMovie(ArgumentMatchers.any());
    }

    @Test
    void testAddMovie_whenNotValid(){


    }

    @Test
    void testAddMovie_whenPersistenceError() throws Exception {

        String movieJsonToSend = JsonProvider.movieJSON(
                "Avatar 1",2018, 180, "Action,Sci-fi,Adventure"
        );

        BDDMockito.given(movieService.addMovie(ArgumentMatchers.any()))
                        .willThrow(new DataIntegrityViolationException("Unique Constraint Violation"));

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieJsonToSend)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

}