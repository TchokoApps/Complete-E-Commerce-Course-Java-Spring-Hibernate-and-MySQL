package com.tchokoapps.springboot.ecommerce.backend.service;

import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import com.tchokoapps.springboot.ecommerce.backend.exception.UserNotFoundException;
import com.tchokoapps.springboot.ecommerce.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testFindAll() {
        // Given
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEnabled(true);

        userRepository.save(user);

        // When
        List<User> users = userService.findAll();

        // Then
        assertFalse(users.isEmpty());
    }

    @Test
    public void testSave() {
        // Given
        User user = new User();
        user.setEmail("user2@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEnabled(true);

        // When
        userService.save(user);

        // Then
        assertNotNull(user.getId());
        assertEquals(user.getEmail(), userRepository.findById(user.getId()).get().getEmail());
        assertTrue(passwordEncoder.matches("password", userRepository.findById(user.getId()).get().getPassword()));
    }

    @Test
    public void testFindUserById() throws UserNotFoundException {
        // Given
        User user = new User();
        user.setEmail("user3@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEnabled(true);

        userRepository.save(user);

        // When
        User foundUser = userService.findUserById(user.getId());

        // Then
        assertEquals(user.getEmail(), foundUser.getEmail());
    }
}
