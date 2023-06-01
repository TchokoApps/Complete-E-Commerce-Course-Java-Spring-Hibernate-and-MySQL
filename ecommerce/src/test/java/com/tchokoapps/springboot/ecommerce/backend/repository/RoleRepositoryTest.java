package com.tchokoapps.springboot.ecommerce.backend.repository;

import com.tchokoapps.springboot.ecommerce.backend.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void createRole() {
        Role role = new Role();
        role.setName("Admin");
        role.setDescription("Admin Role");

        Role roleSaved = roleRepository.save(role);
        assertThat(role.getId()).isNotNull();
        assertThat(role.getName()).isEqualTo(roleSaved.getName());
    }

    @Test
    public void createManyRoles() {

        Role role = new Role();
        role.setName("ADMIN_ROLE");
        role.setDescription("Can perform all actions");

        Role role2 = new Role();
        role2.setName("MANAGER_ROLE");
        role2.setDescription("Can view and edit own data");

        Role role3 = new Role();
        role3.setName("DEVELOPER_ROLE");
        role3.setDescription("Can manage resources and team");

        List<Role> roles = Arrays.asList(role, role2, role3);

        List<Role> rolesSaved = (List<Role>) roleRepository.saveAll(roles);

        rolesSaved.forEach(role1 -> assertThat(role1.getId()).isNotNull());
    }

}