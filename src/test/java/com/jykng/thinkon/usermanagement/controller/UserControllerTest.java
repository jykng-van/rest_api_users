package com.jykng.thinkon.usermanagement.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.jykng.thinkon.usermanagement.entity.User;
import com.jykng.thinkon.usermanagement.repository.UserRepository;

import jakarta.activation.DataSource;


@SpringBootTest(classes = {UserController.class})
@AutoConfigureMockMvc
@EnableWebMvc
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    DataSource dataSource;

    @MockBean
    UserRepository userRepository;

    @InjectMocks
    UserController userController;

    User user;

    @BeforeEach
    public void setUp(){
        user = User.builder()
        .id(1)
        .firstName("John")
        .lastName("Smith")
        .email("jsmith@test.com")
        .phoneNumber("555-555-5555")
        .build();
    }

    @Test
        public void testAddEmployee() throws Exception
        {
                // precondition
                given(userRepository.save(user)).willReturn(user);

                // action
                ResultActions response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writer().writeValueAsString(user)));

                // verify
                response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(user.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(user.getEmail())));
        }
        @Test
        public void testGetEmployeeById() throws Exception{
                //precondition
                given(userRepository.findById(1)).willReturn(Optional.of(user));

                //action
                ResultActions response = mockMvc.perform(get("/users/{id}", user.getId()));

                //verify
                response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName",
                        is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(user.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(user.getEmail())));
        }
        @Test
        public void testGetEmployees() throws Exception{
                //precondition
                List<User> users = new ArrayList<>();
                users.add(user);
                User user2 = User.builder()
                .id(2)
                .firstName("Jane")
                .lastName("Doe")
                .email("janed@test.com")
                .phoneNumber("555-555-5555")
                .build();
                users.add(user2);
                given(userRepository.findAll()).willReturn(users);

                //action
                ResultActions response = mockMvc.perform(get("/users"));

                //verify
                response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                is(users.size())));
        }
        @Test
        public void testUpdateEmployee() throws Exception{ //though only the case of modifying an existing one
                // precondition
                given(userRepository.save(user)).willReturn(user);
                given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
                user.setFirstName("Joe");
                user.setLastName("Bloggs");

                // action
                ResultActions response = mockMvc.perform(put("/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writer().writeValueAsString(user)));

                // verify
                response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName",
                        is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(user.getLastName())));
        }
        @Test
        public void testDeleteEmployee() throws Exception{
                //precondition
                given(userRepository.save(user)).willReturn(user);
                willDoNothing().given(userRepository).deleteById(user.getId());

                //action
                ResultActions response = mockMvc.perform(delete("/users/{id}", user.getId()));

                // verify
                response.andDo(print())
                .andExpect(status().isNoContent());

        }
}
