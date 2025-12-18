package org.example.movieapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Lombok
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {
        "movieId", "title", "releaseYear"
})
@Setter
@Getter
//JPA
@Entity //On dit que la classe movie on va vouloir la save en BDD
//@Table(name = "t_Movie", schema = "Cinema") //Pour "tuner" la table correspondante
@NamedEntityGraph(
        name = "Movie.directorAndActors",
        attributeNodes = {
                @NamedAttributeNode("director"),
                @NamedAttributeNode("actors")
        }
)
public class Movie {
    @Id //Indiquer que le champ suivant est la clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Comment générer la clé primaire
    private Integer movieId;

    @Column(nullable = false, length = 350) //Comme @Table mais pour les champs
    private String title;

    //Du fait que c'est un type primitif, il est de base "nullable = false"
    private int releaseYear;

    //Le fait que ce soit un type Objet, il est "nullable = true"
    private Integer duration;

    @ManyToOne(fetch = FetchType.LAZY) //Par défaut --> EAGER (= On va chercher systématiquement l'objet). LAZY --> C'est que quand on demandera
    @JoinColumn(name = "director_id")
    private Person director;

    @ManyToMany //Fetch = LAZY par défaut
    @JoinTable(
            name = "play",
            inverseJoinColumns = @JoinColumn(name = "actor_id"), //Désigne l'entité "opposée" de type Person
            joinColumns = @JoinColumn(name = "movie_id")
    )
    @Singular
    //private List<Person> actors = new ArrayList<>(); //Pour les collections, on initialise (même à vide) pour éviter des catstrophes
    //On passe par un set (ensemble) car ca permet de ne pas prêter importance à l'ordre
    private Set<Person> actors = new HashSet<>();
}
