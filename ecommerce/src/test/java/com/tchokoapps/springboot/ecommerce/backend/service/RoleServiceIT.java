package com.tchokoapps.springboot.ecommerce.backend.service;

import com.tchokoapps.springboot.ecommerce.backend.entity.Role;
import com.tchokoapps.springboot.ecommerce.backend.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class RoleServiceIT {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Test
    public void testFindAll() {

        // Given
        Role role1 = new Role(1, "ADMIN", "Can perform all actions",
                new HashSet<>(), LocalDateTime.now(), LocalDateTime.now());
        roleRepository.save(role1);

        Role role2 = new Role(2, "USER", "Can view and edit own data",
                new HashSet<>(), LocalDateTime.now(), LocalDateTime.now());
        roleRepository.save(role2);

        // When
        List<Role> result = roleService.findAll();

        // Then
        assertFalse(result.isEmpty());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(0).getId());
    }
}
