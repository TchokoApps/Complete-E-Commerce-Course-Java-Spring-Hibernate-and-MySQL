package com.tchokoapps.springboot.ecommerce.backend.service;

import com.tchokoapps.springboot.ecommerce.backend.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
}
