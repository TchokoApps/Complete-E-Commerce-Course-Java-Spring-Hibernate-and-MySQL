package com.tchokoapps.springboot.ecommerce.backend.service.impl;

import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import com.tchokoapps.springboot.ecommerce.backend.exception.CategoryNotFoundException;
import com.tchokoapps.springboot.ecommerce.backend.repository.CategoryRepository;
import com.tchokoapps.springboot.ecommerce.backend.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findAllHierarchically() {

        List<Category> categories = new ArrayList<>();
        List<Category> rootCategories = categoryRepository.findAllByParentIsNullOrderByNameAsc();
        for (Category rootCategory : rootCategories) {
            getAllChildCategories(rootCategory, categories, "");
        }

        return categories;
    }

    private void getAllChildCategories(Category category, List<Category> categories, String prefix) {
        category.setName(prefix + category.getName());
        categories.add(category);
        List<Category> children = categoryRepository.findAllByParentIdOrderByNameAsc(category.getId());
        if (children != null) {
            for (Category child : children) {
                getAllChildCategories(child, categories, prefix + "-");
            }
        }
    }

    @Override
    public void save(@NotNull Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Category findById(@NotNull Integer id) throws CategoryNotFoundException {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(String.format("Could not find any Category(id=%s)", id)));
    }

    @Override
    public void delete(Integer id) throws CategoryNotFoundException {
        Category category = findById(id);
        categoryRepository.delete(category);
        log.info("Category(id={}) deleted successfully", id);
    }

    @Override
    public Category findByName(String name) throws CategoryNotFoundException {
        return categoryRepository.findByName(name).orElseThrow(() -> new CategoryNotFoundException(String.format("Could not find any Category(name=%s)", name)));
    }
}
