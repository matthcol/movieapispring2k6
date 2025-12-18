package org.example.movieapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personId;
    private String name;
    private LocalDate birthdate;

    @OneToMany(mappedBy = "director")
    private Set<Movie> directedMovies;

    @ManyToMany(mappedBy = "actors")
    private Set<Movie> playedMovies;
}
