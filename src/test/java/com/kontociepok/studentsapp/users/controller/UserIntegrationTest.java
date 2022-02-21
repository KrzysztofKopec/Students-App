package com.kontociepok.studentsapp.users.controller;

import com.kontociepok.studentsapp.courses.controller.CourseResponse;
import com.kontociepok.studentsapp.courses.model.Course;
import com.kontociepok.studentsapp.courses.repository.CourseRepository;
import com.kontociepok.studentsapp.grades.GradeCreate;
import com.kontociepok.studentsapp.grades.GradeResponse;
import com.kontociepok.studentsapp.users.model.User;
import com.kontociepok.studentsapp.users.repository.UserRepository;
import com.kontociepok.studentsapp.users.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseRepository courseRepository;


    @Test
    void shouldReturnAllUsersWhenExist() {
        // given
        userRepository.deleteAll();
        userRepository.save(new User("Alek", "Bartek"));

        // when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/users", UserResponse[].class);

        // then
        assertThat(result.getStatusCodeValue()==200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).hasSize(1);
        assertThat(result.getBody()).containsExactly(new UserResponse(1L, "Alek"));
    }
    @Test
    void shouldReturnUserByIdWhenExist() {
        // given
        userRepository.deleteAll();
        userRepository.save(new User("Alek", "banan"));

        // when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/users/4", UserResponse.class);

        // then
        assertThat(result.getStatusCodeValue()==200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(new UserResponse(4L,"Alek"));
    }
    @Test
    void shouldSaveUser() {
        // given
        userRepository.deleteAll();

        // when
        var result = restTemplate.postForEntity("http://localhost:" + port + "/users",
                new UserCreateDto("Alek","Bartek"),UserResponse.class);

        // then
        assertThat(result.getStatusCodeValue()==200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(new UserResponse(5L,"Alek"));
    }
    @Test
    void shouldDeleteUserByIdWhenExist() {
        // given
        userRepository.deleteAll();
        userRepository.save(new User("Alek", "Krzysztof"));
        userRepository.save(new User("Tomek", "Bartek"));

        // when
        restTemplate.delete("http://localhost:" + port + "/users/3", User.class);

        // then
        var result = restTemplate.getForEntity("http://localhost:" + port + "/users", UserResponse[].class);
        assertThat(result.getStatusCodeValue()==200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).hasSize(1);
        assertThat(result.getBody()).containsExactly(new UserResponse(2L, "Alek"));
    }
    @Test
    void shouldReturnAllCoursesUserWhenExist(){
        //given
        userRepository.deleteAll();
        User user = new User("Alek", "Bartek");
        Course course = new Course("Biologia","ABC");
        userRepository.save(user);
        courseRepository.save(course);
        restTemplate.put("http://localhost:" + port + "/users/1/courses/1", CourseResponse.class);

        //when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/users/1/courses", CourseResponse[].class);

        //then
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).hasSize(1);
        assertThat(result.getBody()).contains(new CourseResponse(1L,"Biologia"));
    }
    @Test
    void shouldAddCourseToUser(){
        //given
        User user = new User("Alek", "Bartek");
        Course course = new Course("Biologia","ABC");
        userRepository.save(user);
        courseRepository.save(course);

        //when
        restTemplate.put("http://localhost:" + port + "/users/1/courses/1", CourseResponse.class);

        //then
        var result = restTemplate.getForEntity("http://localhost:" + port + "/users/1/courses", CourseResponse[].class);
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).hasSize(1);
        assertThat(result.getBody()).contains(new CourseResponse(1L,"Biologia"));
    }
    @Test
    void shouldAddGradeToUser(){

        //given
        User user = new User("Alek", "Bartek");
        Course course = new Course("Biologia","ABC");
        courseRepository.save(course);
        user.addCourse(course);
        userRepository.save(user);

        //when
        restTemplate.put("http://localhost:" + port + "/users/addGrade",
                new GradeCreate(1L,1L,2), GradeCreate.class);

        //then
        var result = restTemplate.getForEntity("http://localhost:" + port + "/users/1/gradesCourses", GradeResponse[].class);
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).hasSize(1);
        assertThat(result.getBody()).contains(new GradeResponse("Biologia",2));
    }
    @Test
    void shouldNotAddABadGrade(){

        //given
        User user = new User("Alek", "Bartek");
        Course course = new Course("Biologia","ABC");
        courseRepository.save(course);
        user.addCourse(course);
        userRepository.save(user);

        //when
        restTemplate.put("http://localhost:" + port + "/users/addGrade",
                new GradeCreate(1L,1L,8), GradeCreate.class);

        //then
        var result = restTemplate.getForEntity("http://localhost:" + port + "/users/1/gradesCourses", GradeResponse[].class);
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).hasSize(0);
    }
    @Test
    void shouldReturnAllGradesUser(){

        //given
        User user = new User("Alek", "Bartek");
        Course course = new Course("Biologia","ABC");
        courseRepository.save(course);
        user.addCourse(course);
        userRepository.save(user);
        restTemplate.put("http://localhost:" + port + "/users/addGrade",
                new GradeCreate(1L,1L,2), GradeCreate.class);
        restTemplate.put("http://localhost:" + port + "/users/addGrade",
                new GradeCreate(1L,1L,3), GradeCreate.class);

        //when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/users/1/gradesCourses", GradeResponse[].class);

        //then
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).hasSize(2);
        assertThat(result.getBody()).contains(new GradeResponse("Biologia",2));
        assertThat(result.getBody()).contains(new GradeResponse("Biologia",3));
    }
}