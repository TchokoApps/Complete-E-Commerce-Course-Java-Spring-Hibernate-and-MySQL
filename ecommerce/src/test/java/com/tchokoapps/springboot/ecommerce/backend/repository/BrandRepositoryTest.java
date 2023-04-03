package com.tchokoapps.springboot.ecommerce.backend.repository;

import com.tchokoapps.springboot.ecommerce.backend.entity.Brand;
import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
                .build();

        System.out.println("Brand before saved: " + brand);

        Brand brandSaved = brandRepository.save(brand);

        System.out.println("Brand after saved: " + brand);

        assertThat(brandSaved.getId()).isNotNull();

    }

    @Test
    public void testFindAllBrandsAndOrderByName() {

        Brand brand1 = Brand.builder()
                .name("EcoGo")
                .photo("EcoGo.png")
                .build();

        Brand brand2 = Brand.builder()
                .name("GlowUp")
                .photo("GlowUp.png")
                .build();

        Brand brand3 = Brand.builder()
                .name("SavvySavings")
                .photo("SavvySavings.png")
                .build();

        Brand brand4 = Brand.builder()
                .name("LuxLounge")
                .photo("LuxLounge.png")
                .build();

        Brand brand5 = Brand.builder()
                .name("Organic Oasis")
                .photo("OrganicOasis.png")
                .build();

        brandRepository.saveAll(Arrays.asList(brand1, brand2, brand3, brand4, brand5));

        List<Brand> brandsOrderedByName = brandRepository.findAllByOrderByNameAsc();

        brandsOrderedByName.forEach(brand -> System.out.println(brand));

        List<String> resultNames = brandsOrderedByName.stream().map(Brand::getName).toList();

        List<String> expectedNames = Arrays.asList("EcoGo", "GlowUp", "LuxLounge", "Organic Oasis", "SavvySavings");

        assertThat(resultNames.toArray()).containsExactly(expectedNames.toArray());
    }

}