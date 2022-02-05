package com.kontociepok.studentsapp.users.controller;

import java.util.List;

public class GradeResponse {
    private final String courseName;
    private final List<Integer> gradesList;

    public GradeResponse(String courseName, List<Integer> gradesList) {
        this.courseName = courseName;
        this.gradesList = gradesList;
    }

    @Override
    public String toString() {
        return "GradeResponse{" +
                "courseName=" + courseName +
                ", gradesList=" + gradesList +
                '}';
    }

    public String getCourseName() {
        return courseName;
    }

    public List<Integer> getGradesList() {
        return gradesList;
    }
}
