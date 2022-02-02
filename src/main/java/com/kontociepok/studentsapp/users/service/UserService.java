package com.kontociepok.studentsapp.users.service;

import com.kontociepok.studentsapp.courses.controller.CourseResponse;
import com.kontociepok.studentsapp.courses.model.Course;
import com.kontociepok.studentsapp.courses.repository.CourseRepository;
import com.kontociepok.studentsapp.users.controller.UserCreateDto;
import com.kontociepok.studentsapp.users.controller.UserResponse;
import com.kontociepok.studentsapp.users.model.User;
import com.kontociepok.studentsapp.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private CourseRepository courseRepository;

    @Autowired
    public UserService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(this::convertToUserResponse).collect(Collectors.toList());
    }

    public UserResponse findById(long userId) {
        return convertToUserResponse(userRepository.getById(userId));
    }

    public UserResponse createUser(UserCreateDto userCreateDto) {
        var user = new User(userCreateDto.getFirstName(), userCreateDto.getLastName());
        return convertToUserResponse(userRepository.save(user));
    }

    public UserResponse deleteUser(long userId) {
        User user = userRepository.getById(userId);
        userRepository.deleteById(userId);
        return convertToUserResponse(user);
    }

    public CourseResponse addCourseToUser(long userId, long courseId) {
        User user = userRepository.getById(userId);
        Course course = courseRepository.getById(courseId);
        user.addCourse(course);
        userRepository.save(user);
        return new CourseResponse(course.getId(), course.getName());
    }

    public List<CourseResponse> allCoursesOfUser(long userId) {
        return userRepository.getById(userId).getCourses().stream().map(e -> new CourseResponse(e.getId(),e.getName()))
                .collect(Collectors.toList());
    }

    private UserResponse convertToUserResponse(User user) {
        return new UserResponse(user.getId(), user.getFirstName());
    }
}
