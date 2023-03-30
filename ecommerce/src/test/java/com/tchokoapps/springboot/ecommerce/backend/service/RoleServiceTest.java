package com.tchokoapps.springboot.ecommerce.backend.service;

import com.tchokoapps.springboot.ecommerce.backend.entity.Role;
import com.tchokoapps.springboot.ecommerce.backend.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    private RoleService roleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        roleService = new RoleService(roleRepository);
    }

    @Test
    public void testFindAll() {
        // Given
        List<Role> roles = Arrays.asList(new Role(1, "ADMIN", "Can perform all actions", LocalDateTime.now(), LocalDateTime.now()),
                new Role(2, "USER", "Can view and edit own data", LocalDateTime.now(), LocalDateTime.now()));
        doReturn(roles).when(roleRepository).findAll();

        // When
        List<Role> result = roleService.findAll();

        // Then
        assertEquals(roles.size(), result.size());
        assertEquals(roles.get(0).getId(), result.get(0).getId());
        assertEquals(roles.get(0).getName(), result.get(0).getName());
        assertEquals(roles.get(1).getId(), result.get(1).getId());
        assertEquals(roles.get(1).getName(), result.get(1).getName());
    }

}