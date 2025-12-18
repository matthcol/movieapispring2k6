package org.example.movieapi.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class MovieSimpleDto extends MovieCreateDto{
    private int movieId;
}
