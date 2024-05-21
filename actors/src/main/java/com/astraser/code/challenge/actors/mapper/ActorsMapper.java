package com.astraser.code.challenge.actors.mapper;

import com.astraser.code.challenge.actors.dto.ActorDto;
import com.astraser.code.challenge.actors.entity.Actor;
import org.springframework.stereotype.Component;

@Component
public class ActorsMapper {

    public ActorDto mapToActorDto(Actor actor, ActorDto actorDto) {
        actorDto.setId(actor.getId());
        actorDto.setFirstName(actor.getFirstName());
        actorDto.setLastName(actor.getLastName());
        actorDto.setBirthDate(actor.getBirthDate());
        actorDto.setGender(actor.getGender());
        return actorDto;
    }


    public Actor mapToActor(ActorDto actorDto, Actor actor) {
        actor.setFirstName(actorDto.getFirstName());
        actor.setLastName(actorDto.getLastName());
        actor.setBirthDate(actorDto.getBirthDate());
        actor.setGender(actorDto.getGender());
        return actor;
    }
}
