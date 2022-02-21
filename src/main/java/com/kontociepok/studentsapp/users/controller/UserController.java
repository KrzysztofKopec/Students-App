package com.kontociepok.studentsapp.users.controller;

import com.kontociepok.studentsapp.courses.controller.CourseResponse;
import com.kontociepok.studentsapp.grades.GradeCreate;
import com.kontociepok.studentsapp.grades.GradeResponse;
import com.kontociepok.studentsapp.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    List<UserResponse> findAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/users/{userId}")
    UserResponse getUser(@PathVariable long userId) {
        return userService.findById(userId);
    }

    @GetMapping("/users/{userId}/courses")
    List<CourseResponse> allCoursesOfUser(@PathVariable long userId){
        return userService.allCoursesOfUser(userId);
    }

    @GetMapping("/users/{userId}/gradesCourses")
    List<GradeResponse> allgradesCoursesOfUser(@PathVariable long userId){
        return userService.allgradesCoursesOfUser(userId);
    }

    @DeleteMapping("users/{userId}")
    UserResponse deleteUser(@PathVariable long userId){
        return userService.deleteUser(userId);
    }

    @PostMapping("/users")
    UserResponse createUser(@Valid @RequestBody UserCreateDto userCreateDto){
        return userService.createUser(userCreateDto);
    }

    @PutMapping("/users/{userId}/courses/{courseId}")
    public CourseResponse addCourseToUser(@PathVariable long userId, @PathVariable long courseId) {
        return userService.addCourseToUser(userId, courseId);
    }

    @PutMapping("/users/addGrade")
    void addGrade(@Valid @RequestBody GradeCreate gradeCreate){
        userService.addGrade(gradeCreate);
    }
}
