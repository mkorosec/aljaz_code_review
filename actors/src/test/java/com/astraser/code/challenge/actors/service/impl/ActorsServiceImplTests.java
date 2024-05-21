package com.astraser.code.challenge.actors.service.impl;

import com.astraser.code.challenge.actors.configuration.ActorsServiceImplConfiguration;
import com.astraser.code.challenge.actors.dto.ActorDto;
import com.astraser.code.challenge.actors.enums.Gender;
import com.astraser.code.challenge.actors.exception.ActorNotFoundException;
import com.astraser.code.challenge.actors.service.ActorsService;
import com.astraser.code.challenge.actors.utils.ActorsTestUtils;
import com.github.tomakehurst.wiremock.WireMockServer;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ActorsServiceImplConfiguration.class})
@ActiveProfiles("test")
@Transactional
class ActorsServiceImplTests {

    @Autowired
    private WireMockServer actorMoviesWiremock;

    @Autowired
    private ActorsService actorsService;

    @Autowired
    private ActorsTestUtils actorsTestUtils;

    @BeforeEach
    void setUp() throws IOException {
        actorsTestUtils.setupMockActorMovie(actorMoviesWiremock);
    }


    @Test
    void testCreateUpdateDeleteActor() {

        actorsTestUtils.setupMockActorMovieDelete(actorMoviesWiremock);

        //Create actor
        ActorDto actor = actorsTestUtils.createActorDto(1L);
        ActorDto created = actorsService.create(actor);

        actorsTestUtils.validateActor(actor, created);

        //Update actor
        created.setFirstName("Updated First Name");
        created.setLastName("Updated Last Name");
        created.setBirthDate(LocalDate.of(1990, 1, 1));
        created.setGender(Gender.FEMALE);

        actorsService.update(created.getId(), created);

        ActorDto updated = actorsService.read(created.getId(), true);

        actorsTestUtils.validateActor(created, updated);
        actorsTestUtils.validateActorMovies(updated);

        //Delete actor
        actorsService.delete(updated.getId());

        assertThrows(ActorNotFoundException.class, () -> actorsService.read(updated.getId(), false));
    }


    @Test
    void testRetrieveActorsPaginated() {

        List<ActorDto> actors = actorsTestUtils.createActorDtosList().stream().toList();
        actors.forEach(a -> {
            ActorDto actorDto = actorsService.create(a);
            a.setId(actorDto.getId());
        });

        Page<ActorDto> paginatedListFirst = actorsService.listPaginated(0, 5);

        assertNotNull(paginatedListFirst.getContent());
        assertEquals(5, paginatedListFirst.getContent().size());

        assertArrayEquals(actors.stream().limit(5).toArray(), paginatedListFirst.getContent().toArray());

        Page<ActorDto> paginatedListSecond = actorsService.listPaginated(1, 5);

        assertNotNull(paginatedListSecond.getContent());
        assertEquals(2, paginatedListSecond.getContent().size());

        assertArrayEquals(actors.subList(5, 7).toArray(), paginatedListSecond.getContent().toArray());


    }

    @Test
    void testRetrieveActors() {
        List<ActorDto> actors = actorsTestUtils.createActorDtosList().stream().toList();
        actors.forEach(a -> {
            ActorDto actorDto = actorsService.create(a);
            a.setId(actorDto.getId());
        });

        Collection<ActorDto> list = actorsService.list();
        assertEquals(7, list.size());
        assertArrayEquals(actors.toArray(), list.toArray());

    }


}