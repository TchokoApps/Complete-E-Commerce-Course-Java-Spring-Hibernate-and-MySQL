package com.tchokoapps.springboot.ecommerce.backend.service;

import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import com.tchokoapps.springboot.ecommerce.backend.exception.CategoryNotFoundException;
import com.tchokoapps.springboot.ecommerce.backend.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void save(@NotNull Category category) {
        categoryRepository.save(category);
    }

    public Category findById(@NotNull Integer id) throws CategoryNotFoundException {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(String.format("Could not find any Category with id = %s", id)));
    }

    public void delete(Integer id) throws CategoryNotFoundException {
        Category category = findById(id);
        categoryRepository.delete(category);
        log.info("Category with id = {} deleted successfully", id);
    }

    public Category findByName(String name) throws CategoryNotFoundException {
        return categoryRepository.findCategoryByName(name).orElseThrow(() -> new CategoryNotFoundException(String.format("Could not find any Category with name = %s", name)));
    }


}
