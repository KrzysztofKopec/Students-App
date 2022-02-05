package com.kontociepok.studentsapp.users.repository;

import com.kontociepok.studentsapp.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
