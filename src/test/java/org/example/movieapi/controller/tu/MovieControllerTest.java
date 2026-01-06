package org.example.movieapi.controller.tu;

import org.example.movieapi.controller.MovieController;
import org.example.movieapi.dto.MovieDetailedDto;
import org.example.movieapi.service.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
                .andDo(MockMvcResultHandlers.print())
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

        //Préparer le composant Mock:
        Mockito.when(movieService.getMovie(movieId))
                .thenReturn(Optional.of(
                        MovieDetailedDto.builder()
                                .movieId(movieId)
                                .title(title)
                                .releaseYear(releaseYear)
                                .duration(duration)
                                .build()
                ));

        //Appeler le controller via le composant "mockMvc":
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URI + "/{movieId}", movieId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.movieId").value(movieId),
                        //$ désigne l'objet qu'on reçoit
                        MockMvcResultMatchers.jsonPath("$.title").value(title),
                        MockMvcResultMatchers.jsonPath("$.releaseYear").value(releaseYear),
                        MockMvcResultMatchers.jsonPath("$.duration").value(duration)
                )
        ;
        //On va vérifier que le MockService a bien été appelé:
        BDDMockito.then(movieService)
                .should()
                .getMovie(movieId)
        ;
    }

}