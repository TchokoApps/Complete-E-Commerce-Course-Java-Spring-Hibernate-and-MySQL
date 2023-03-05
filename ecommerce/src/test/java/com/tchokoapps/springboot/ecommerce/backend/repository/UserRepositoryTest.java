package com.tchokoapps.springboot.ecommerce.backend.repository;

import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindUserByEmail() {
        // Given
        User user = new User();
        user.setEmail("johndoe@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password");
        user.setEnabled(true);
        userRepository.save(user);

        // When
        User foundUser = userRepository.findUserByEmail(user.getEmail());

        // Then
        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

}