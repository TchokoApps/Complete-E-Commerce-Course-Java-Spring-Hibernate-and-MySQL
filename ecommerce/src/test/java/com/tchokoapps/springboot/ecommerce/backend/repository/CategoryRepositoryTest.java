package com.tchokoapps.springboot.ecommerce.backend.repository;

import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void whenSavedThenCategoryShouldBePersisted() {
        Category category = Category.builder()
                .name("Apparel and Accessories")
                .alias("Apparel and Accessories")
                .image("Apparel-and-Accessories.jpg")
                .build();
        Category categorySaved = categoryRepository.save(category);
        assertThat(categorySaved.getId()).isNotNull();
    }

    @Test
    public void whenCategoryAndSubcategoryCreatedThenPersistBoth() {
        Category category = Category.builder()
                .name("Apparel and Accessories")
                .alias("Apparel and Accessories")
                .image("Apparel and Accessories.jpg")
                .enabled(true)
                .build();
        Category categorySaved = categoryRepository.save(category);

        Category subCategory = Category.builder()
                .name("Computers and Electronics")
                .alias("Computers and Electronics")
                .image("Computers and Electronics.jpg")
                .enabled(true)
                .parent(category)
                .build();

        Category subCategorySaved = categoryRepository.save(subCategory);

        assertThat(subCategorySaved.getParent().getId()).isEqualTo(categorySaved.getId());
    }

}