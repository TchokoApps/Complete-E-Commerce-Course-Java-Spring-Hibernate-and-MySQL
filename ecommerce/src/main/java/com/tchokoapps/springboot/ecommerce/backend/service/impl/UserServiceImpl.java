package com.tchokoapps.springboot.ecommerce.backend.service.impl;

import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import com.tchokoapps.springboot.ecommerce.backend.exception.UserNotFoundException;
import com.tchokoapps.springboot.ecommerce.backend.repository.UserRepository;
import com.tchokoapps.springboot.ecommerce.backend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public void save(@NonNull User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(Integer id) throws UserNotFoundException {
        User userFound = findUserById(id);
        userRepository.delete(userFound);
        log.info("delete() :: User with id = {} delete successfully.", id);
    }

    @Override
    public User findUserById(@NotNull Integer id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Could not found any User with Id = " + id));
    }

    @Override
    public User findUserByEmail(@NotNull String email) throws UserNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException(String.format("Could not find user with email '%s'", email)));
    }
}
