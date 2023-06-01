package com.tchokoapps.springboot.ecommerce.backend.service;

import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import com.tchokoapps.springboot.ecommerce.backend.exception.UserNotFoundException;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserService {

    List<User> findAll();

    void save(@NonNull User user);

    void delete(Integer id) throws UserNotFoundException;

    User findUserById(@NotNull Integer id) throws UserNotFoundException;

    User findUserByEmail(@NotNull String email) throws UserNotFoundException;
}
