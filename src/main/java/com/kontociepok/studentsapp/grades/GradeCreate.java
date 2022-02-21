package com.kontociepok.studentsapp.grades;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class GradeCreate {

    private final Long userId;
    private final Long courseId;

    @Min(1)
    @Max(6)
    private final Integer grade;

    public GradeCreate(Long userId, Long courseId, @Min(1) @Max(6)Integer grade) {
        this.userId = userId;
        this.courseId = courseId;
        this.grade = grade;
    }

    public Long getUserId() { return userId; }

    public Long getCourseId() {
        return courseId;
    }

    public Integer getGrade() {
        return grade;
    }
}
