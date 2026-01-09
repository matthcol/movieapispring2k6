package org.example.movieapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Mise en place du composant (Bean) ModelMapper

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        //On pourrait rajouter toutes configurations de ce ModelMapper
        //Exemple: Attribut "releaseYear" côté entity correspond à "year" côté DTO

        return new ModelMapper();
    }
}
