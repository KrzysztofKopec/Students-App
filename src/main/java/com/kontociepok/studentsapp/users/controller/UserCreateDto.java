package com.kontociepok.studentsapp.users.controller;

import javax.validation.constraints.NotBlank;

public class UserCreateDto {

    @NotBlank(message = "please enter first name")
    private final String firstName;
    @NotBlank(message = "please enter last name")
    private final String lastName;

    public UserCreateDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
