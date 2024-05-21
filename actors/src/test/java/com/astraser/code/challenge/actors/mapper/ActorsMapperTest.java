package com.astraser.code.challenge.actors.mapper;

import com.astraser.code.challenge.actors.configuration.ActorsTestConfiguration;
import com.astraser.code.challenge.actors.dto.ActorDto;
import com.astraser.code.challenge.actors.entity.Actor;
import com.astraser.code.challenge.actors.utils.ActorsTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ActorsTestConfiguration.class})
class ActorsMapperTest {

    @Autowired
    private ActorsMapper actorsMapper;

    @Autowired
    private ActorsTestUtils actorsTestUtils;


    @Test
    void mapToActorDto() {
        Actor actor = actorsTestUtils.createActor(1L);
        ActorDto actorDto = actorsMapper.mapToActorDto(actor, new ActorDto());

        assertAll(
                () -> assertEquals(actor.getFirstName(), actorDto.getFirstName()),
                () -> assertEquals(actor.getLastName(), actorDto.getLastName()),
                () -> assertEquals(actor.getId(), actorDto.getId()),
                () -> assertEquals(actor.getGender(), actorDto.getGender()),
                () -> assertEquals(actor.getBirthDate(), actorDto.getBirthDate())
        );
    }

    @Test
    void mapToActor() {
        ActorDto actorDto = actorsTestUtils.createActorDto(1L);
        Actor actor = actorsMapper.mapToActor(actorDto, new Actor());

        assertAll(
                () -> assertEquals(actor.getFirstName(), actorDto.getFirstName()),
                () -> assertEquals(actor.getLastName(), actorDto.getLastName()),
                () -> assertEquals(actor.getGender(), actorDto.getGender()),
                () -> assertEquals(actor.getBirthDate(), actorDto.getBirthDate())
        );
    }
}