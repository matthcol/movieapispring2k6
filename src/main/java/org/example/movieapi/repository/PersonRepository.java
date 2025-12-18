package org.example.movieapi.repository;

import org.example.movieapi.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person,Integer> {

    @Query("""
            SELECT p
            FROM Person p
            WHERE lower(p.name) like %:name%
            """)

    List<Person> findByName (String name);
}
