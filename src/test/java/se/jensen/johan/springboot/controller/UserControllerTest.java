package se.jensen.johan.springboot.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import se.jensen.johan.springboot.dto.UserResponseDto;
import se.jensen.johan.springboot.model.User;
import se.jensen.johan.springboot.repository.UserRepository;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        User user = new User();
        user.setRole("ADMIN");
        user.setPassword(passwordEncoder.encode("password"));
        user.setEmail("admin@gmail.com");
        user.setUsername("admin");
        user.setDisplayName("admin display");
        user.setBio("admin bio");
        userRepository.save(user);

    }

    @Test
    void shouldGetAllUsers() throws Exception {
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/users")
                                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "password")))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String response = result.getResponse().getContentAsString();
        List<UserResponseDto> users = objectMapper.readValue(
                response, new TypeReference<List<UserResponseDto>>() {
                });
        Assertions.assertEquals(1, users.size());
    }

    @Test
    void shouldGetUserById() throws Exception {

        Long id = userRepository.findAll().get(0).getId();

        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/users/" + id)
                                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "password")))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();

        String response = result.getResponse().getContentAsString();

        UserResponseDto user = objectMapper.readValue(
                response, UserResponseDto.class
        );

        Assertions.assertEquals(id, user.id());
        Assertions.assertEquals("admin", user.username());
    }


    @Test
    void shouldGetMe() throws Exception {

        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/users/me")
                                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "password")))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();

        String response = result.getResponse().getContentAsString();

        UserResponseDto me = objectMapper.readValue(
                response, UserResponseDto.class
        );

        Assertions.assertEquals("admin", me.username());
        Assertions.assertEquals("admin@gmail.com", me.email());
    }

}
