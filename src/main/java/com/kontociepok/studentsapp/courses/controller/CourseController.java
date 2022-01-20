package com.kontociepok.studentsapp.courses.controller;

import com.kontociepok.studentsapp.courses.model.Course;
import com.kontociepok.studentsapp.courses.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CourseController {

    @Autowired
    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses")
    List<CourseResponse> findAllCourses(){
        return courseRepository.findAll().stream().map(this::convertToCourseResponse).collect(Collectors.toList());
    }

    @GetMapping("/courses/{courseId}")
    CourseResponse getCourse(@PathVariable long courseId) {
        return courseRepository.findById(courseId).map(this::convertToCourseResponse).orElse(null);
    }

    @PostMapping("/courses")
    CourseResponse createCourse(@Valid @RequestBody CourseCreateDto courseCreateDto){
        return convertToCourseResponse(courseRepository.save(new Course(courseCreateDto.getName(), courseCreateDto.getDescription())));
    }

    private CourseResponse convertToCourseResponse(Course course) {
        return new CourseResponse(course.getId(), course.getName());
    }


}
