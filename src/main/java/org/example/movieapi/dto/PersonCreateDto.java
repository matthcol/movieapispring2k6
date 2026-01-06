package org.example.movieapi.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonCreateDto {
    private String name;
    private LocalDate birthdate;
}
