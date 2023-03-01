package com.tchokoapps.springboot.ecommerce.backend.repository;

import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByEmail(String email);
}
