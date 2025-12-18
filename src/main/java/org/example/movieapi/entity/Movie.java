package org.example.movieapi.entity;

import jakarta.persistence.*;
import lombok.*;

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
public class Movie {
    @Id //Indiquer que le champ suivant est la clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Comment générer la clé primaire
    private Integer movieId;
    private String title;
    private int releaseYear;
    private Integer duration;

    @ManyToOne(fetch = FetchType.LAZY) //Par défaut --> EAGER (= On va chercher systématiquement l'objet). LAZY --> C'est que quand on demandera
    @JoinColumn(name = "director_id")
    private Person director;
}
