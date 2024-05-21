package com.astraser.code.challenge.actors.service.impl;

import com.astraser.code.challenge.actors.controller.ActorsController;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class ActorsServiceImpl implements ActorsService {


    private static final Logger logger = LoggerFactory.getLogger(ActorsController.class);

    private ActorsRepository actorsRepository;
    private ActorsMapper actorsMapper;
    private ActorMovieClientHelper actorMovieClientHelper;

    @Override
    public ActorDto create(ActorDto actorDto) {
        logger.info("Creating actor");
        actorsRepository.findByFirstNameAndLastName(actorDto.getFirstName(), actorDto.getLastName())
                .ifPresent(actor -> {
                    throw new ActorAlreadyExistsException(String.format("Actor with firstName %s and lastName %s already exists.", actorDto.getFirstName(), actorDto.getLastName()));
                });
        Actor actor = actorsRepository.save(actorsMapper.mapToActor(actorDto, new Actor()));
        logger.info("Created actor with id: {}", actor.getId());
        return actorsMapper.mapToActorDto(actor, new ActorDto());
    }

    @Override
    public ActorDto read(Long id, boolean includeMovies) {

        logger.info("Reading actor with id: {}", id);

        ActorDto actorDto = actorsRepository.findById(id).map(actor -> actorsMapper.mapToActorDto(actor, new ActorDto())).
                orElseThrow(() -> new ActorNotFoundException(String.format("Actor with id %s not found.", id)));

        if (includeMovies) {
            actorMovieClientHelper.getActorMovies(actorDto);
        }
        return actorDto;
    }

    @Override
    public ActorDto update(Long id, ActorDto actorDto) {

        logger.info("Updating actor with id {}: ", id);

        if (!id.equals(actorDto.getId())) {
            throw new ActorValidationException(String.format("Request id %s is not matching body id: %s", id, actorDto.getId()));
        }
        Actor existingActor = actorsRepository.findById(id).orElseThrow(() -> new ActorNotFoundException(String.format("Actor with id %s not found.", id)));
        actorsMapper.mapToActor(actorDto, existingActor);
        actorsRepository.save(existingActor);

        return actorsMapper.mapToActorDto(existingActor, new ActorDto());
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting actor with id {}: ", id);
        Actor actor = actorsRepository.findById(id).orElseThrow(() -> new ActorNotFoundException(String.format("Actor with id %s not found.", id)));
        actorsRepository.delete(actor);
        actorMovieClientHelper.deleteActorMovies(actor.getId());

    }

    @Override
    public Page<ActorDto> listPaginated(Integer page, Integer size) {
        logger.info("List actors with page {} and size {}: ", page, size);
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
        logger.info("List all actors.");
        return actorsRepository.findAll().stream().map(actor -> {
            ActorDto actorDto = actorsMapper.mapToActorDto(actor, new ActorDto());
            actorMovieClientHelper.getActorMovies(actorDto);
            return actorDto;
        }).toList();
    }
}
