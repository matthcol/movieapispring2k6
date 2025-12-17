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

    //On fait nous mêmes la requête (en JPQL):
    @Query("""
              SELECT m
              FROM Movie m
              WHERE m.releaseYear > :yearMin
              AND UPPER(m.title) like :title
              ORDER BY m.releaseYear, m.title
              """)
    List<Movie> findByTitleYear (String title, int yearMin);

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
