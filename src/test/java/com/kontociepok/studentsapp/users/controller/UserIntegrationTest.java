package com.kontociepok.studentsapp.users.controller;

import com.kontociepok.studentsapp.users.model.User;
import com.kontociepok.studentsapp.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldReturnAllUsersWhenExist() {
        // given
        userRepository.deleteAll();
        userRepository.save(new User("Alek", "Bartek"));

        // when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/users", UserResponse[].class);

        // then
        var user = new UserResponse(1L, "Alek");
        assertThat(result.getStatusCodeValue()==200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).containsExactly(user);
    }

}