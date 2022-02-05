package com.kontociepok.studentsapp;

import com.kontociepok.studentsapp.courses.model.Course;
import com.kontociepok.studentsapp.courses.repository.CourseRepository;
import com.kontociepok.studentsapp.users.model.User;
import com.kontociepok.studentsapp.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentsappApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(StudentsappApplication.class, args);
	}

	@Override
	public void run(String... args) {
		User user = new User("Tomek", "banan");
		User user1 = new User("Mietek", "orange");

		userRepository.save(user);
		userRepository.save(user1);

		Course course = new Course("Informatyka","Java");
		Course course1 = new Course("Matematyka","Algebra");

		courseRepository.save(course);
		courseRepository.save(course1);

	}
}
