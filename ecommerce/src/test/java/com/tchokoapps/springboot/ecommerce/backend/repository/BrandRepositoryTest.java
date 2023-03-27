package com.tchokoapps.springboot.ecommerce.backend.repository;

import com.tchokoapps.springboot.ecommerce.backend.entity.Brand;
import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BrandRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void testCreateBrands() {

        Category category = Category.builder()
                .name("Laptops and Tablets")
                .photo("Laptops_and_Tablets.png")
                .alias("Laptops and Tablets")
                .enabled(true)
                .build();

        Category category2 = Category.builder()
                .name("Laptops")
                .photo("Laptops.png")
                .alias("Laptops")
                .enabled(true)
                .build();

        Category categorySaved = categoryRepository.save(category);
        Category categorySaved2 = categoryRepository.save(category2);

        System.out.println("categorySaved: " + categorySaved);
        System.out.println("categorySaved2: " + categorySaved2);

        HashSet<Category> categories = new HashSet<>();
        categories.add(categorySaved);
        categories.add(categorySaved2);

        Brand brand = Brand.builder()
                .name("EasyShop")
                .photo("EasyShop.png")
                .categories(categories)
                .build();

        System.out.println("Brand before saved: " + brand);

        Brand brandSaved = brandRepository.save(brand);

        System.out.println("Brand after saved: " + brand);

        assertThat(brandSaved.getId()).isNotNull();

    }

}