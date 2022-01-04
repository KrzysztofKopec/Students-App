package com.kontociepok.studentsapp.courses.repository;

import com.kontociepok.studentsapp.courses.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
