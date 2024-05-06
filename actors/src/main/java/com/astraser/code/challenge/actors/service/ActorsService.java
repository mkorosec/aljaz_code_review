package com.astraser.code.challenge.actors.service;

import com.astraser.code.challenge.actors.dto.ActorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.Collection;

public interface ActorsService {

    /**
     *
     * @param actorDto - ActorDto Object
     * @return created ActorDto based on a given id
     */
    ActorDto create(ActorDto actorDto);

    /**
     *
     * @param id - Unique id of actor
     * @return ActorDto object based on a given id
     */
    ActorDto read(Long id);

    /**
     * @param page - Page
     * @param size - Size
     * @return List of ActorDto object by pagination
     */
    Page<ActorDto> listPaginated(Integer page, Integer size);

    /**
     *
     * @return List of ActorDto objects
     */
    Collection<ActorDto> list();

    /**
     *
     * @param actorDto - ActorDto Object
     * @return updated ActorDto object
     */
    ActorDto update(Long id, ActorDto actorDto);

    /**
     *
     * @param id - Unique id of actor
     */
    void delete(Long id);

}
