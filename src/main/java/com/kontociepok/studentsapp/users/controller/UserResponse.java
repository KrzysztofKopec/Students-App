package com.kontociepok.studentsapp.users.controller;

import java.util.Objects;

public class UserResponse {

    private final Long id;
    private final String FirstName;

    public UserResponse(Long id, String firstName) {
        this.id = id;
        FirstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse that = (UserResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(FirstName, that.FirstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, FirstName);
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", FirstName='" + FirstName + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return FirstName;
    }
}
