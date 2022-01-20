package com.kontociepok.studentsapp.users.controller;

import com.kontociepok.studentsapp.users.model.User;
import com.kontociepok.studentsapp.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    List<UserResponse> findAllUsers(){
        return userRepository.findAll().stream().map(this::convertToUserResponse).collect(Collectors.toList());
    }

    @GetMapping("/users/{userId}")
    UserResponse getUser(@PathVariable long userId) {
        return userRepository.findById(userId).map(this::convertToUserResponse).orElse(null);
    }

    @PostMapping("/users")
    UserResponse createUser(@Valid @RequestBody UserCreateDto userCreateDto){
        return convertToUserResponse(userRepository.save(new User(userCreateDto.getFirstName(), userCreateDto.getLastName())));
    }

    private UserResponse convertToUserResponse(User user) {
        return new UserResponse(user.getId(), user.getFirstName());
    }
}
