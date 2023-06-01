package com.tchokoapps.springboot.ecommerce.backend.service;

import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import com.tchokoapps.springboot.ecommerce.backend.exception.CategoryNotFoundException;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    List<Category> findAllHierarchically();

    void save(@NotNull Category category);

    Category findById(@NotNull Integer id) throws CategoryNotFoundException;

    void delete(Integer id) throws CategoryNotFoundException;

    Category findByName(String name) throws CategoryNotFoundException;
}
