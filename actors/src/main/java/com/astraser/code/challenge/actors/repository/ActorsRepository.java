package com.astraser.code.challenge.actors.repository;

import com.astraser.code.challenge.actors.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorsRepository extends JpaRepository<Actor, Long> {

    Optional<Actor> findByFirstNameAndLastName(String firstName, String lastName);

}
