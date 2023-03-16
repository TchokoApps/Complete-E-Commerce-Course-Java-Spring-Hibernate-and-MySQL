package com.tchokoapps.springboot.ecommerce.common.bootstrap;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.tchokoapps.springboot.ecommerce.backend.entity.Role;
import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import com.tchokoapps.springboot.ecommerce.backend.repository.RoleRepository;
import com.tchokoapps.springboot.ecommerce.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@AllArgsConstructor
public class FakeObjectsCreator implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        Faker faker = Faker.instance();

        for (int i = 0; i < 10; i++) {
            Name name = faker.name();
            User user = new User();
            user.setEmail(name.firstName() + "." + name.lastName() + "@example.com");
            String password = faker.internet().password();
            user.setPassword(password);
            user.setFirstName(name.firstName());
            user.setLastName(name.lastName());
            user.setEnabled(true);

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
