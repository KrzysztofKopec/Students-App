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
    void shouldReturnSaveCourseWhenExists(){
        //given
        courseRepository.deleteAll();
        var course = new Course("Biology", "xyz");

        //when
        var result = courseRepository.save(course);

        //then
        assertThat(result.getName()).isEqualTo("Biology");
    }

    @Test
    void shouldReturnCourseWhenExists(){
        //given
        courseRepository.deleteAll();
        var course = new Course("Info","Basic");
        courseRepository.save(course);

        //when
        var result = courseRepository.getById(1L);

        //then
        assertThat(result.getName()).isEqualTo("Info");
    }

    @Test
    void shouldReturnAllCoursesWhenExists(){
        //given
        courseRepository.deleteAll();
        var course = new Course("History", "Kazimierz");
        courseRepository.save(course);

        //when
        var result = courseRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void shouldDeleteCourseByIdWhenExists(){
        //given
        courseRepository.deleteAll();
        var course = new Course("Math", "Pierwiastki");
        courseRepository.save(course);

        //when
        courseRepository.deleteById(1L);

        //then
        assertThat(courseRepository.findAll().size()).isEqualTo(0);
    }
}