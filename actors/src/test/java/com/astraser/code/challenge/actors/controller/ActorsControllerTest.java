package com.astraser.code.challenge.actors.controller;

import com.astraser.code.challenge.actors.configuration.ActorsTestConfiguration;
import com.astraser.code.challenge.actors.dto.ActorDto;
import com.astraser.code.challenge.actors.service.ActorsService;
import com.astraser.code.challenge.actors.utils.ActorsTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ActorsController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ActorsTestConfiguration.class})
class ActorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorsService actorsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActorsTestUtils actorsTestUtils;


    @Test
    public void getActor() throws Exception {
        ActorDto actorDto = actorsTestUtils.createActorDto(1L);

        when(actorsService.read(actorDto.getId(), true)).thenReturn(actorDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/{id}", actorDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(actorDto.getId()))
                .andExpect(jsonPath("$.firstName").value("First Name Test 1"))
                .andExpect(jsonPath("$.lastName").value("Last Name Test 1"))
                .andExpect(jsonPath("$.birthDate").value("1989-01-01"))
                .andExpect(jsonPath("$.gender").value("MALE"))

        ;
    }

    @Test
    public void createActor() throws Exception {
        ActorDto actorDto = actorsTestUtils.createActorDto(1L);
        when(actorsService.create(actorDto)).thenReturn(actorDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("First Name Test 1"))
                .andExpect(jsonPath("$.lastName").value("Last Name Test 1"))
                .andExpect(jsonPath("$.birthDate").value("1989-01-01"))
                .andExpect(jsonPath("$.gender").value("MALE"))

        ;
    }

    @Test
    public void updateActor() throws Exception {
        ActorDto actorDto = actorsTestUtils.createActorDto(1L);

        when(actorsService.update(actorDto.getId(), actorDto)).thenReturn(actorDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/{id}", actorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(actorDto.getId()))
                .andExpect(jsonPath("$.firstName").value("First Name Test 1"))
                .andExpect(jsonPath("$.lastName").value("Last Name Test 1"))
                .andExpect(jsonPath("$.birthDate").value("1989-01-01"))
                .andExpect(jsonPath("$.gender").value("MALE"))

        ;
    }

    @Test
    public void listActorsWithoutPagination() throws Exception {
        when(actorsService.list()).thenReturn(actorsTestUtils.createActorDtosList().stream().limit(2).toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].firstName").value("First Name Test 1"))
                .andExpect(jsonPath("$.[0].lastName").value("Last Name Test 1"))
                .andExpect(jsonPath("$.[0].birthDate").value("1989-01-01"))
                .andExpect(jsonPath("$.[0].gender").value("MALE"))
                .andExpect(jsonPath("$.[1].firstName").value("First Name Test 2"))
                .andExpect(jsonPath("$.[1].lastName").value("Last Name Test 2"))
                .andExpect(jsonPath("$.[1].birthDate").value("1989-01-01"))
                .andExpect(jsonPath("$.[1].gender").value("MALE"));
    }

    @Test
    public void listActorsWithPagination() throws Exception {
        int page = 0;
        int size = 10;

        when(actorsService.listPaginated(page, size)).thenReturn(new PageImpl<>(actorsTestUtils.createActorDtosList().stream().limit(2).toList(), Pageable.ofSize(size), page));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("First Name Test 1"))
                .andExpect(jsonPath("$.content[0].lastName").value("Last Name Test 1"))
                .andExpect(jsonPath("$.content[0].birthDate").value("1989-01-01"))
                .andExpect(jsonPath("$.content[0].gender").value("MALE"))
                .andExpect(jsonPath("$.content[1].firstName").value("First Name Test 2"))
                .andExpect(jsonPath("$.content[1].lastName").value("Last Name Test 2"))
                .andExpect(jsonPath("$.content[1].birthDate").value("1989-01-01"))
                .andExpect(jsonPath("$.content[1].gender").value("MALE"));

        ;
    }


}