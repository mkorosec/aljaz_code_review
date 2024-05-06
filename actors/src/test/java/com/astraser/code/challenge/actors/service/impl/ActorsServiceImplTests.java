package com.astraser.code.challenge.actors.service.impl;

import com.astraser.code.challenge.actors.configuration.ActorsServiceImplConfiguration;
import com.astraser.code.challenge.actors.dto.ActorDto;
import com.astraser.code.challenge.actors.dto.MovieDto;
import com.astraser.code.challenge.actors.enums.Gender;
import com.astraser.code.challenge.actors.exception.ActorNotFoundException;
import com.astraser.code.challenge.actors.service.ActorsService;
import com.astraser.code.challenge.actors.service.client.ActorMovieClient;
import com.astraser.code.challenge.actors.utils.ActorsHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.netflix.discovery.converters.Auto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ActorsServiceImplConfiguration.class})
@ActiveProfiles("test")
class ActorsServiceImplTests {

    @Autowired
    private WireMockServer actorMoviesWiremock;


    @Autowired
    private ActorMovieClient actorMovieClient;

    @Autowired
    private ActorsService actorsService;

    @Autowired
    private ActorsHelper actorsHelper;

    @BeforeEach
    void setUp() throws IOException {
        actorsHelper.setupMockActorMovie(actorMoviesWiremock);
    }


    @Test
    void createUpdateAndDeleteActor() {

        ActorDto actor = actorsHelper.createActor();
        ActorDto created = actorsService.create(actor);

        validateActor(actor, created);

        //Update actor
        created.setFirstName("Updated First Name");
        created.setLastName("Updated Last Name");
        created.setBirthDate(LocalDate.of(1990, 1, 1));
        created.setGender(Gender.FEMALE);

        actorsService.update(created.getId(), created);

        ActorDto updated = actorsService.read(created.getId());

        validateActor(created, updated);
        validateActorMovies(updated);

        actorsService.delete(updated.getId());

        assertThrows(ActorNotFoundException.class, () -> actorsService.read(updated.getId()));
    }


    @Test
    void listPaginated() {

        Page<ActorDto> paginatedListFirst = actorsService.listPaginated(0,5);

        assertNotNull(paginatedListFirst.getContent());
        assertEquals(5, paginatedListFirst.getContent().size());

        Page<ActorDto> paginatedListSecond = actorsService.listPaginated(1,5);

        assertNotNull(paginatedListSecond.getContent());
        assertEquals(5, paginatedListSecond.getContent().size());


    }

    @Test
    void list() {
        Collection<ActorDto> list = actorsService.list();
        assertEquals(10, list.size());

    }



    private void validateActor(ActorDto updated, ActorDto original) {
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertEquals(updated.getFirstName(), original.getFirstName());
        assertEquals(updated.getLastName(), original.getLastName());
        assertEquals(updated.getBirthDate(), original.getBirthDate());
        assertEquals(updated.getGender(), original.getGender());
    }

    private void validateActorMovies(ActorDto actorDto) {
        assertNotNull(actorDto.getMovies());
        assertEquals(actorDto.getMovies().size(), 2);

        List<MovieDto> actorDtoList = new ArrayList<>(actorDto.getMovies());
        assertEquals(actorDtoList.get(0).getTitle(), "Title");
        assertEquals(actorDtoList.get(0).getDescription(), "Description");
        assertEquals(actorDtoList.get(0).getRating(), 8.0);
        assertEquals(actorDtoList.get(0).getReleaseDate(), LocalDate.of(2022,12,31));

        assertEquals(actorDtoList.get(1).getTitle(), "Title 2");
        assertEquals(actorDtoList.get(1).getDescription(), "Description 2");
        assertEquals(actorDtoList.get(1).getRating(), 9.0);
        assertEquals(actorDtoList.get(1).getReleaseDate(), LocalDate.of(2022,12,31));


    }


}