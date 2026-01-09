package org.example.movieapi.dto;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String name;

    private LocalDate birthdate;
}
