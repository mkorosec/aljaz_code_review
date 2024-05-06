package com.astraser.code.challenge.actors.controller;

import com.astraser.code.challenge.actors.dto.ActorDto;
import com.astraser.code.challenge.actors.enums.Gender;
import com.astraser.code.challenge.actors.service.ActorsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ActorsController.class)
@AutoConfigureMockMvc
class ActorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorsService actorsService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void getActor() throws Exception {
        Long actorId = 1L;
        ActorDto actorDto = new ActorDto();
        actorDto.setId(actorId);
        actorDto.setFirstName("First name");
        actorDto.setLastName("Last name");
        actorDto.setBirthDate(LocalDate.now());
        actorDto.setGender(Gender.MALE);

        when(actorsService.read(actorId)).thenReturn(actorDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/{id}", actorId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(actorId))
                .andExpect(jsonPath("$.firstName").value("First name"))
                .andExpect(jsonPath("$.lastName").value("Last name"))
                .andExpect(jsonPath("$.birthDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.gender").value("MALE"))

        ;
    }

    @Test
    public void createActor() throws Exception {
        Long actorId = 1L;
        ActorDto actorDto = new ActorDto();
        actorDto.setId(actorId);
        actorDto.setFirstName("First name");
        actorDto.setLastName("Last name");
        actorDto.setBirthDate(LocalDate.now());
        actorDto.setGender(Gender.MALE);

        when(actorsService.create(actorDto)).thenReturn(actorDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(actorId))
                .andExpect(jsonPath("$.firstName").value("First name"))
                .andExpect(jsonPath("$.lastName").value("Last name"))
                .andExpect(jsonPath("$.birthDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.gender").value("MALE"))

        ;
    }

    @Test
    public void updateActor() throws Exception {
        Long actorId = 1L;
        ActorDto actorDto = new ActorDto();
        actorDto.setId(actorId);
        actorDto.setFirstName("First name");
        actorDto.setLastName("Last name");
        actorDto.setBirthDate(LocalDate.now());
        actorDto.setGender(Gender.MALE);

        when(actorsService.update(actorId, actorDto)).thenReturn(actorDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/{id}",actorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(actorId))
                .andExpect(jsonPath("$.firstName").value("First name"))
                .andExpect(jsonPath("$.lastName").value("Last name"))
                .andExpect(jsonPath("$.birthDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.gender").value("MALE"))

        ;
    }
    @Test
    public void testListActorsWithoutPagination() throws Exception {
        when(actorsService.list()).thenReturn(createActorsList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].firstName").value("First name"))
                .andExpect(jsonPath("$.[0].lastName").value("Last name"))
                .andExpect(jsonPath("$.[0].birthDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.[0].gender").value("MALE"))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].firstName").value("First name 2"))
                .andExpect(jsonPath("$.[1].lastName").value("Last name 2"))
                .andExpect(jsonPath("$.[1].birthDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.[1].gender").value("FEMALE"));
    }

    @Test
    public void testListActorsWithPagination() throws Exception {
        int page = 0;
        int size = 10;

        when(actorsService.listPaginated(page, size)).thenReturn(new PageImpl<>(createActorsList().stream().toList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].firstName").value("First name"))
                .andExpect(jsonPath("$.content[0].lastName").value("Last name"))
                .andExpect(jsonPath("$.content[0].birthDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.content[0].gender").value("MALE"))
                .andExpect(jsonPath("$.content[1].id").value("2"))
                .andExpect(jsonPath("$.content[1].firstName").value("First name 2"))
                .andExpect(jsonPath("$.content[1].lastName").value("Last name 2"))
                .andExpect(jsonPath("$.content[1].birthDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.content[1].gender").value("FEMALE"));

        ;
    }


    private Collection<ActorDto> createActorsList()
    {
        Long actorId = 1L;
        ActorDto actorDto = new ActorDto();
        actorDto.setId(actorId);
        actorDto.setFirstName("First name");
        actorDto.setLastName("Last name");
        actorDto.setBirthDate(LocalDate.now());
        actorDto.setGender(Gender.MALE);

        Long actorId2 = 2L;
        ActorDto actorDto2 = new ActorDto();
        actorDto2.setId(actorId2);
        actorDto2.setFirstName("First name 2");
        actorDto2.setLastName("Last name 2");
        actorDto2.setBirthDate(LocalDate.now());
        actorDto2.setGender(Gender.FEMALE);

        return Arrays.asList(actorDto,actorDto2);
    }

}