package org.example.movieapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank //Equivalent de "@NotNull" mais ajoute le cas de la chaîne vide ("")
    @Size(max = 250)
    private String title;

    @NotNull
    @Min(1850)
    private int releaseYear;

    private Integer duration;

    private Set<String> genres = new HashSet<>();
}
