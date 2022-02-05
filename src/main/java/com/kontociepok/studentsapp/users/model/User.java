package com.kontociepok.studentsapp.users.model;

import com.kontociepok.studentsapp.courses.model.Course;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "users_courses",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "course_id") }
    )
    private Set<Course> courses;

    private Map<Long, List<Integer>> gradesCourses;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        gradesCourses = new HashMap<>();
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course){
        if(courses == null){
            courses = new HashSet<>();
        }
        courses.add(course);
    }

    public Map<Long, List<Integer>> getGradesCourses() {
        return gradesCourses;
    }

    public void addingACourseGrade(Long courseId, Integer grade) {
        gradesCourses.computeIfAbsent(courseId, k -> new ArrayList<>()).add(grade);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
