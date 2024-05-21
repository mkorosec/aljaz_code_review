package com.astraser.code.challenge.actors.movies.repository;

import com.astraser.code.challenge.actors.movies.entity.ActorMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ActorsMoviesRepository extends JpaRepository<ActorMovie, Long> {

    Optional<ActorMovie> findByActorIdAndMovieId(Long actorId, Long movieId);

    Collection<ActorMovie> findAllByActorId(Long actorId);

    Collection<ActorMovie> findAllByMovieId(Long movieId);

    void deleteByActorId(Long actorId);

    void deleteByMovieId(Long movieId);
}
