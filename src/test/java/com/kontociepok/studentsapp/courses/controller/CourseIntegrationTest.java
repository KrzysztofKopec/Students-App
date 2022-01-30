package com.kontociepok.studentsapp.courses.controller;

import com.kontociepok.studentsapp.courses.model.Course;
import com.kontociepok.studentsapp.courses.repository.CourseRepository;
import com.kontociepok.studentsapp.users.controller.UserResponse;
import com.kontociepok.studentsapp.users.model.User;
import com.kontociepok.studentsapp.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void shouldReturnAllCoursesWhenExist() {
        // given
        courseRepository.deleteAll();
        courseRepository.save(new Course("Fizyka", "Atom"));

        // when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/courses", CourseResponse[].class);

        // then
        assertThat(result.getStatusCodeValue()==200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).hasSize(1);
        assertThat(result.getBody()).containsExactly(new CourseResponse(1L, "Fizyka"));
    }
    @Test
    void shouldReturnCourseByIdWhenExist() {
        // given
        courseRepository.deleteAll();
        courseRepository.save(new Course("Fizyka", "Atom"));

        // when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/courses/1", CourseResponse.class);

        // then
        assertThat(result.getStatusCodeValue()==200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(new CourseResponse(1L,"Fizyka"));
    }
    @Test
    void shouldSaveUser() {
        // given
        courseRepository.deleteAll();

        // when
        var result = restTemplate.postForEntity("http://localhost:" + port + "/courses",
                new CourseCreateDto("Math","Sin"),CourseResponse.class);

        // then
        assertThat(result.getStatusCodeValue()==200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(new CourseResponse(1L,"Math"));
    }
    @Test
    void shouldDeleteCourseByIdWhenExist() {
        // given
        courseRepository.deleteAll();
        courseRepository.save(new Course("Fizyka","Atom"));
        courseRepository.save(new Course("Math","Sin"));

        // when
        restTemplate.delete("http://localhost:" + port + "/courses/2", User.class);

        // then
        var result = restTemplate.getForEntity("http://localhost:" + port + "/courses", CourseResponse[].class);
        assertThat(result.getStatusCodeValue()==200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).hasSize(1);
        assertThat(result.getBody()).containsExactly(new CourseResponse(1L, "Fizyka"));
    }
    @Test
    void shouldReturnAllUsersCourseWhenExist(){
        courseRepository.deleteAll();
        User user = new User("Alek", "Bartek");
        Course course = new Course("Biologia","ABC");
        userRepository.save(user);
        courseRepository.save(course);
        restTemplate.put("http://localhost:" + port + "/courses/1/users/1", UserResponse.class);

        //when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/courses/1/users", UserResponse[].class);

        //then
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).hasSize(1);
        assertThat(result.getBody()).contains(new UserResponse(1L,"Alek"));
    }
    @Test
    void shouldAddUserToCourse(){
        //given
        User user = new User("Alek", "Bartek");
        Course course = new Course("Biologia","ABC");
        userRepository.save(user);
        courseRepository.save(course);

        //when
        restTemplate.put("http://localhost:" + port + "/courses/1/users/1", UserResponse.class);

        //then
        var result = restTemplate.getForEntity("http://localhost:" + port + "/courses/1/users", UserResponse[].class);
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).hasSize(1);
        assertThat(result.getBody()).contains(new UserResponse(1L,"Alek"));
    }
}
