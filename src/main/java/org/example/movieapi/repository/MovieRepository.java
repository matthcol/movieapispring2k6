package org.example.movieapi.repository;

import org.example.movieapi.entity.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    //Utilisation de Query Lookup (cf tableau)
    //https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

    List<Movie> findByReleaseYearBetweenOrderByReleaseYear(int year1, int year2);

    //A partir d'un mot dans le tire en ignorant la casse et année après
    List<Movie> findByTitleContainingIgnoreCaseAndReleaseYearGreaterThan (String title, int yearMin, Sort sort);

    // NB: IgnoreCase pas possible sur la propriété de l'objet associé director
    List<Movie> findByDirectorNameContaining(String name);

    //On fait nous mêmes la requête (en JPQL):
    @Query("""
              SELECT m
              FROM Movie m
              WHERE m.releaseYear > :yearMin
              AND UPPER(m.title) like :title
              ORDER BY m.releaseYear, m.title
              """)
    List<Movie> findByTitleYear (String title, int yearMin);

    @Query("""
            SELECT m
            FROM Movie m
            JOIN FETCH m.director d
            WHERE d.name like %:name%
            """)//Le FETCH permet, pour cette requête, de remplir m.director avec l'objet associé
    List<Movie> findByDirectorName (String name, Sort sort);

    /**
     * Find movies by the director name
     * @param name pattern à chercher en minuscule
     * @param sort sort criteria(s)
     * @return list of movie found
     */
    @Query("""
            SELECT m
            FROM Movie m
            JOIN FETCH m.director d
            WHERE lower(d.name) like :name
            """)//Le FETCH permet, pour cette requête, de remplir m.director avec l'objet associé
    List<Movie> findByDirectorNameCI (String name, Sort sort);

    @NativeQuery(
            """
                    SELECT m.movie_id, m.title, m.release_year, m.duration
                    FROM movie m
                    WHERE m.release_year <= :yearMax
                    AND m.duration > :durMin
                    ORDER BY m.duration desc
                    """
    )
    List<Movie> findByYearDuration (int yearMax, int durMin);
}
