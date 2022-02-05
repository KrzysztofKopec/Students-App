package com.kontociepok.studentsapp.users.controller;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class GradeCreate {

    private final Long courseId;

    @Min(1)
    @Max(6)
    private final Integer grade;

    public GradeCreate(Long courseId, @Min(1) @Max(6)Integer grade) {
        this.courseId = courseId;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "GradeCreate{" +
                "courseId=" + courseId +
                ", grade=" + grade +
                '}';
    }

    public Long getCourseId() {
        return courseId;
    }

    public Integer getGrade() {
        return grade;
    }
}
