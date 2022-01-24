package com.kontociepok.studentsapp.courses.controller;

import com.kontociepok.studentsapp.users.controller.UserResponse;
import com.kontociepok.studentsapp.courses.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    List<CourseResponse> findAllCourses(){
        return courseService.findAll();
    }

    @GetMapping("/courses/{courseId}")
    CourseResponse getCourse(@PathVariable long courseId) {
        return courseService.findById(courseId);
    }

    @GetMapping("/courses/{courseId}/users")
    List<UserResponse> allUsersOfCourse(@PathVariable long courseId){
        return courseService.allUsersOfCourse(courseId);
    }

    @DeleteMapping("courses/{courseId}")
    CourseResponse deleteCourse(@PathVariable long courseId){
        return courseService.deleteCourse(courseId);
    }

    @PostMapping("/courses")
    CourseResponse createCourse(@Valid @RequestBody CourseCreateDto courseCreateDto){
        return courseService.createCourse(courseCreateDto);
    }

    @PutMapping("/courses/{courseId}/users/{userId}")
    public UserResponse addUserToCourse(@PathVariable long courseId, @PathVariable long userId) {
        return courseService.addUserToCourse(userId, courseId);
    }
}
