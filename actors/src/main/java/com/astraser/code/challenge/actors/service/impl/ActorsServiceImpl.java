package com.astraser.code.challenge.actors.service.impl;

import com.astraser.code.challenge.actors.dto.ActorDto;
import com.astraser.code.challenge.actors.entity.Actor;
import com.astraser.code.challenge.actors.exception.ActorAlreadyExistsException;
import com.astraser.code.challenge.actors.exception.ActorNotFoundException;
import com.astraser.code.challenge.actors.exception.ActorValidationException;
import com.astraser.code.challenge.actors.helper.ActorMovieClientHelper;
import com.astraser.code.challenge.actors.mapper.ActorsMapper;
import com.astraser.code.challenge.actors.repository.ActorsRepository;
import com.astraser.code.challenge.actors.service.ActorsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class ActorsServiceImpl implements ActorsService {

    private ActorsRepository actorsRepository;
    private ActorsMapper actorsMapper;
    private ActorMovieClientHelper actorMovieClientHelper;

    @Override
    public ActorDto create(ActorDto actorDto) {
        actorsRepository.findByFirstNameAndLastName(actorDto.getFirstName(), actorDto.getLastName())
                .ifPresent(actor -> {
                    throw new ActorAlreadyExistsException(String.format("Actor with firstName %s and lastName %s already exists.", actorDto.getFirstName(), actorDto.getLastName()));
                });
        Actor actor = actorsRepository.save(actorsMapper.mapToActor(actorDto, new Actor()));
        return actorsMapper.mapToActorDto(actor, new ActorDto());
    }

    @Override
    public ActorDto read(Long id) {

        ActorDto actorDto = actorsRepository.findById(id).map(actor -> actorsMapper.mapToActorDto(actor, new ActorDto())).
                orElseThrow(() -> new ActorNotFoundException(String.format("Actor with id %s not found.", id)));

        actorMovieClientHelper.getActorMovies(actorDto);

        return actorDto;
    }

    @Override
    public ActorDto update(Long id, ActorDto actorDto) {
        if(!id.equals(actorDto.getId()))
        {
            throw new ActorValidationException(String.format("Request id {} is not matching body id: {}", id, actorDto.getId()));
        }
        Actor existingActor = actorsRepository.findById(id).orElseThrow(() -> new ActorNotFoundException(String.format("Actor with id %s not found.", id)));
        actorsMapper.mapToActor(actorDto, existingActor);
        actorsRepository.save(existingActor);
        return actorsMapper.mapToActorDto(existingActor, new ActorDto());
    }

    @Override
    public void delete(Long id) {
        Actor actor = actorsRepository.findById(id).orElseThrow(() -> new ActorNotFoundException(String.format("Actor with id %s not found.", id)));
        actorsRepository.delete(actor);
        actorMovieClientHelper.deleteActorMovies(actor.getId());
    }

    @Override
    public Page<ActorDto> listPaginated(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Actor> actorPage = actorsRepository.findAll(paging);
        return actorPage.map(actor -> {
            ActorDto actorDto = actorsMapper.mapToActorDto(actor, new ActorDto());
            actorMovieClientHelper.getActorMovies(actorDto);
            return actorDto;
        });
    }

    @Override
    public Collection<ActorDto> list() {
        return actorsRepository.findAll().stream().map(actor -> {
            ActorDto actorDto = actorsMapper.mapToActorDto(actor, new ActorDto());
            actorMovieClientHelper.getActorMovies(actorDto);
            return actorDto;
        }).toList();
    }
}
