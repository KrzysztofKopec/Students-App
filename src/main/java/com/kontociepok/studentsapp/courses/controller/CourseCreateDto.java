package com.kontociepok.studentsapp.courses.controller;

import javax.validation.constraints.NotBlank;

public class CourseCreateDto {

    @NotBlank(message = "please enter name")
    private final String name;
    @NotBlank(message = "please enter description")
    private final String description;

    public CourseCreateDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
