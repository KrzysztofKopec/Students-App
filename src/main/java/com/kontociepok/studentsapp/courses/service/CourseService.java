package com.kontociepok.studentsapp.courses.service;

import com.kontociepok.studentsapp.courses.controller.CourseCreateDto;
import com.kontociepok.studentsapp.courses.controller.CourseResponse;
import com.kontociepok.studentsapp.courses.model.Course;
import com.kontociepok.studentsapp.courses.repository.CourseRepository;
import com.kontociepok.studentsapp.users.controller.UserResponse;
import com.kontociepok.studentsapp.users.model.User;
import com.kontociepok.studentsapp.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    CourseRepository courseRepository;
    UserRepository userRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public List<CourseResponse> findAll() {
        return courseRepository.findAll().stream().map(this::convertToCourseResponse).collect(Collectors.toList());
    }

    public CourseResponse findById(long userId) {
        return convertToCourseResponse(courseRepository.getById(userId));
    }

    public CourseResponse createCourse(CourseCreateDto courseCreateDto) {
        return convertToCourseResponse(courseRepository.save(new Course(courseCreateDto.getName(), courseCreateDto.getDescription())));
    }

    public CourseResponse deleteCourse(long courseId) {
        Course course = courseRepository.getById(courseId);
        courseRepository.deleteById(courseId);
        return convertToCourseResponse(course);
    }

    public UserResponse addUserToCourse(long userId, long courseId) {
        User user = userRepository.getById(userId);
        Course course = courseRepository.getById(courseId);
        course.addStudent(user);
        courseRepository.save(course);
        return new UserResponse(user.getId(), user.getFirstName());
    }

    public List<UserResponse> allUsersOfCourse(long courseId) {
        return courseRepository.getById(courseId).getStudents().stream().map(e -> new UserResponse(e.getId(),e.getFirstName()))
                .collect(Collectors.toList());
    }

    private CourseResponse convertToCourseResponse(Course course) {
        return new CourseResponse(course.getId(), course.getName());
    }
}
