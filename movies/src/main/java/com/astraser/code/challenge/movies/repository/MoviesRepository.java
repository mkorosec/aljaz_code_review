package com.astraser.code.challenge.movies.repository;

import com.astraser.code.challenge.movies.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoviesRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByTitle(String title);
}
