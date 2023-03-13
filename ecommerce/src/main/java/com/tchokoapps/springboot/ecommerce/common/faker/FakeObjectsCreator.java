package com.tchokoapps.springboot.ecommerce.common.faker;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.tchokoapps.springboot.ecommerce.backend.entity.Role;
import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import com.tchokoapps.springboot.ecommerce.backend.repository.RoleRepository;
import com.tchokoapps.springboot.ecommerce.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class FakeObjectsCreator implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    @Autowired
    public FakeObjectsCreator(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Faker faker = Faker.instance();

        for (int i = 0; i < 1000; i++) {
            Name name = faker.name();
            User user = new User();
            user.setEmail(name.firstName() + "." + name.lastName() + "@example.com");
            user.setPassword(faker.internet().password());
            user.setFirstName(name.firstName());
            user.setLastName(name.lastName());
            user.setEnabled(true);

            Set<Role> roles = new HashSet<>();
            int number = faker.number().numberBetween(1, 11);
            roles.add(roleRepository.findById(number).orElseThrow(() -> new RuntimeException("Role Not Found")));
            user.setRoles(roles);

            userRepository.save(user);
        }
    }
}
