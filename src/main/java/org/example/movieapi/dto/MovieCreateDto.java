package org.example.movieapi.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
//JSON n'a pas besoin d'annotation par défaut.
public class MovieCreateDto {
    private String title;
    private int releaseYear;
    private Integer duration;
    private Set<String> genres = new HashSet<>();
}
