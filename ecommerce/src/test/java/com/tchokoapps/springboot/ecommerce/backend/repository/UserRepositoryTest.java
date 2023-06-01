package com.tchokoapps.springboot.ecommerce.backend.repository;

import com.tchokoapps.springboot.ecommerce.backend.entity.Role;
import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateUser() {

        User user = new User();
        user.setEmail("user1@example.com");
        user.setPassword("password1");
        user.setConfirmPassword("password1");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPhoto("photo1.jpg");
        user.setEnabled(true);

        Role role = new Role();
        role.setName("ADMIN_ROLE");
        role.setDescription("Administrator role");

        user.getRoles().add(role);
        role.getUsers().add(user);

        User userSaved = userRepository.save(user);
        Role roleSaved = roleRepository.save(role);

        assertThat(userSaved.getId()).isNotNull();
        assertThat(roleSaved.getId()).isNotNull();
    }

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
        User foundUser = userRepository.findUserByEmail(user.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with email %s doesn´t exist", user.getEmail())));

        // Then
        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

}