package com.tchokoapps.springboot.ecommerce.backend.repository;

import com.tchokoapps.springboot.ecommerce.backend.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void createRole() {
        Role role = new Role();
        role.setName("Admin");
        role.setDescription("Admin Role");

        Role roleSaved = roleRepository.save(role);
        assertThat(role.getId()).isNotNull();
        assertThat(role.getName()).isEqualTo(roleSaved.getName());
    }

}