package com.kontociepok.studentsapp.users.repository;

import com.kontociepok.studentsapp.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
