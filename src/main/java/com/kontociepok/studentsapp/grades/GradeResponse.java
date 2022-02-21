package com.kontociepok.studentsapp.grades;

import java.util.Objects;

public class GradeResponse {
    private final String courseName;
    private final Integer grade;

    public GradeResponse(String courseName, Integer grade) {
        this.courseName = courseName;
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradeResponse that = (GradeResponse) o;
        return Objects.equals(courseName, that.courseName) &&
                Objects.equals(grade, that.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseName, grade);
    }

    @Override
    public String toString() {
        return "GradeResponse{" +
                "courseName=" + courseName +
                ", grade=" + grade +
                '}';
    }

    public String getCourseName() {
        return courseName;
    }

    public Integer getGrade() {
        return grade;
    }
}
