package org.example.movieapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

//Lombok
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
//JPA
@Entity //On dit que la classe movie on va vouloir la save en BDD
public class Movie {
    @Id //Indiquer que le champ suivant est la clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Comment générer la clé primaire
    private Integer movieId;
    private String title;
    private int releaseYear;
    private Integer duration;



}
