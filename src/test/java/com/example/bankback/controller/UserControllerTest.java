package com.example.bankback.controller;


import com.example.bankback.data.dto.UserDTO;
import com.example.bankback.data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO createUserDto() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Shepard");
        userDTO.setBirthDate(LocalDate.of(1980, 1, 1));
        userDTO.setPassport("1234567890");
        userDTO.setPhoneNumber("123-456-7890");
        userDTO.setEmail("john.shepard@ishoudgo.com");
        userDTO.setAddress("123 Tali St, Presidium, Citadel");
        return userDTO;
    }

    @Test
    void testCreateUser() throws Exception {
        UserDTO userDTO = createUserDto();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(userDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDTO.getLastName()))
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()));
    }

    @Test
    void testGetUser() throws Exception {
        UserDTO userDTO = createUserDto();
        String response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        UserDTO createdUser = objectMapper.readValue(response, UserDTO.class);

        mockMvc.perform(get("/users/{id}", createdUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(userDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDTO.getLastName()))
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()));
    }

    @Test
    void testUpdateUser() throws Exception {

        UserDTO userDTO = createUserDto();
        String response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        UserDTO createdUser = objectMapper.readValue(response, UserDTO.class);


        createdUser.setFirstName("Jane");
        createdUser.setLastName("Doe");
        createdUser.setEmail("jane.doe@example.com");

        mockMvc.perform(put("/users/{id}", createdUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"));
    }

    @Test
    void testDeleteUser() throws Exception {

        UserDTO userDTO = createUserDto();
        String response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        UserDTO createdUser = objectMapper.readValue(response, UserDTO.class);

        mockMvc.perform(delete("/users/{id}", createdUser.getId()))
                .andExpect(status().isOk());


        mockMvc.perform(get("/users/{id}", createdUser.getId()))
                .andExpect(status().isNotFound());
    }
}


