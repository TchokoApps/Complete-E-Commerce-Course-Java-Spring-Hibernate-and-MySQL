package com.tchokoapps.springboot.ecommerce.common.bootstrap;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.tchokoapps.springboot.ecommerce.backend.entity.Role;
import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import com.tchokoapps.springboot.ecommerce.backend.repository.RoleRepository;
import com.tchokoapps.springboot.ecommerce.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Component
public class UserCreator {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public void createUsers() {
        Faker faker = Faker.instance();

        for (int i = 0; i < 25; i++) {

            Name name = faker.name();
            String firstName = name.firstName();
            String lastName = name.lastName();
            String password = faker.internet().password();

            User user = new User();
            user.setEmail(firstName + "." + lastName + "@example.com");
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);

            Set<Role> roles = new HashSet<>();
            int number = faker.number().numberBetween(1, 11);
            roles.add(roleRepository.findById(number).orElseThrow(() -> new RuntimeException("Role Not Found")));
            user.setRoles(roles);

            log.info("User: {}", user);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        }
    }
}
