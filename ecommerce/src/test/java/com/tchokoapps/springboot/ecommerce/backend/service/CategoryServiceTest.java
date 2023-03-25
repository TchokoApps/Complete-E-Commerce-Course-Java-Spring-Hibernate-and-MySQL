package com.tchokoapps.springboot.ecommerce.backend.service;

import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void testFindAllHierarchically() {
        List<Category> allCategories = categoryService.findAllHierarchically();
        allCategories.forEach(category -> System.out.println(category.getName()));
        assertThat(allCategories.isEmpty()).isFalse();
    }

}