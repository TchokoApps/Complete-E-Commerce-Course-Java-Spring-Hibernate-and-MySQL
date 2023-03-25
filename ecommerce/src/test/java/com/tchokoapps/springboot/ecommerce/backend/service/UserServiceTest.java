package com.tchokoapps.springboot.ecommerce.backend.service;

import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import com.tchokoapps.springboot.ecommerce.backend.exception.UserNotFoundException;
import com.tchokoapps.springboot.ecommerce.backend.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void testFindAll() {
        // given
        List<User> userList = new ArrayList<>();

        User user1 = new User();
        user1.setId(1);
        user1.setFirstName("firstName1");
        user1.setLastName("lastName1");
        user1.setPassword("password1");
        userList.add(user1);

        User user2 = new User();
        user2.setId(2);
        user2.setFirstName("firstName2");
        user2.setLastName("lastName2");
        user2.setPassword("password2");
        userList.add(user2);

        doReturn(userList).when(userRepository).findAll();

        // when
        List<User> result = userService.findAll();

        // then
        assertEquals(userList.size(), result.size());
        assertEquals(userList.get(0).getId(), result.get(0).getId());
        assertEquals(userList.get(0).getFirstName(), result.get(0).getFirstName());
        assertEquals(userList.get(1).getId(), result.get(1).getId());
        assertEquals(userList.get(1).getFirstName(), result.get(1).getFirstName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setFirstName("user");
        user.setPassword("password");

        userService.save(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testFindUserById() throws UserNotFoundException {
        User user = new User();
        user.setId(1);
        user.setFirstName("user");
        user.setPassword("password");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = userService.findUserById(1);

        Assertions.assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getPassword(), result.getPassword());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void testFindUserByIdNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findUserById(1));
        verify(userRepository, times(1)).findById(1);
    }
}
