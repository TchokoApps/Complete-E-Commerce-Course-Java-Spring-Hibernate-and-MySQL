package com.tchokoapps.springboot.ecommerce.backend.service;

import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import com.tchokoapps.springboot.ecommerce.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public void save(@NonNull User user) {
        encodeUserPassword(user);
        userRepository.save(user);
    }

    public void delete(Integer id) throws UserNotFoundException {
        User userFound = findUserById(id);
        userRepository.delete(userFound);
        log.info("delete() :: User with id = {} delete successfully.", id);
    }

    private void encodeUserPassword(User user) {
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
    }

    public User findUserById(@NonNull Integer id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Could not found any User with Id = " + id));
    }
}
