package com.kontociepok.studentsapp.users.repository;

import com.kontociepok.studentsapp.users.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase()
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void shouldReturnSaveUserWhenExists(){
        //given
        userRepository.deleteAll();
        var user = new User("Tomek","Cebularz");

        //when
        var result = userRepository.save(user);

        //then
        assertThat(result.equals(new User("Tomek","Cebularz")));
    }

    @Test
    void shouldReturnUserWhenExists(){
        //given
        userRepository.deleteAll();
        var user = new User("Tomek","Cebularz");
        userRepository.save(user);

        //when
        var result = userRepository.getById(1L);

        //then
        assertThat(result.getFirstName()).isEqualTo("Tomek");
    }

    @Test
    void shouldReturnAllUsersWhenExists(){
        //given
        userRepository.deleteAll();
        var user = new User("Tomek","Banan");
        userRepository.save(user);

        //when
        var result = userRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.contains(user)).isTrue();
    }

    @Test
    void shouldDeleteUserByIdWhenExists(){
        //given
        userRepository.deleteAll();
        var user = new User("Tomek","Cebularz");
        userRepository.save(user);

        //when
        userRepository.deleteById(1L);

        //then
        assertThat(userRepository.findAll().size()).isEqualTo(0);
    }
}