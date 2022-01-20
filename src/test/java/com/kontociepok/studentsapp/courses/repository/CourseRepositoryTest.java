package com.kontociepok.studentsapp.courses.repository;

import com.kontociepok.studentsapp.courses.model.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase()
class CourseRepositoryTest {

    @Autowired
    CourseRepository courseRepository;

    @Test
    void shouldReturnSaveUserWhenExists(){
        //given
        courseRepository.deleteAll();
        var course = new Course("Biology");

        //when
        var result = courseRepository.save(course);

        //then
        assertThat(result.getName()).isEqualTo("Biology");
    }

    @Test
    void shouldReturnUserWhenExists(){
        //given
        courseRepository.deleteAll();
        var course = new Course("Info");
        courseRepository.save(course);

        //when
        var result = courseRepository.getById(2L);

        //then
        assertThat(result.getName()).isEqualTo("Info");
    }

    @Test
    void shouldReturnAllUsersWhenExists(){
        //given
        courseRepository.deleteAll();
        var course = new Course("History");
        courseRepository.save(course);

        //when
        var result = courseRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void shouldDeleteUserByIdWhenExists(){
        //given
        courseRepository.deleteAll();
        var course = new Course("Math");
        courseRepository.save(course);

        //when
        courseRepository.deleteById(4L);

        //then
        assertThat(courseRepository.findAll().size()).isEqualTo(0);
    }
}