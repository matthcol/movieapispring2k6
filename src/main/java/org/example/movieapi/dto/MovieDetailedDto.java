package org.example.movieapi.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.movieapi.entity.Person;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class MovieDetailedDto extends MovieSimpleDto{

    private PersonSimpleDto director;

    @Singular //Permet de rajouter 1 par 1
    private Set<PersonSimpleDto> actors = new HashSet<>();
}
