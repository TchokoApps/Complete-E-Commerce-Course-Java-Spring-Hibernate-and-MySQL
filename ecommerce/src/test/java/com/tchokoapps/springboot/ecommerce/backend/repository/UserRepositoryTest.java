package com.tchokoapps.springboot.ecommerce.backend.repository;

import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindUserByEmail() {
        User userFound = userRepository.findUserByEmail("john.doe@gmail.com");
        assertThat(userFound).isNotNull();
    }

}