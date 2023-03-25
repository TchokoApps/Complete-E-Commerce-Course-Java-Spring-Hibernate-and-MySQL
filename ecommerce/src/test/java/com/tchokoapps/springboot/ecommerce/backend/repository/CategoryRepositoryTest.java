package com.tchokoapps.springboot.ecommerce.backend.repository;

import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

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
                .photo("Apparel-and-Accessories.jpg")
                .build();
        Category categorySaved = categoryRepository.save(category);
        assertThat(categorySaved.getId()).isNotNull();
    }

    @Test
    public void whenCategoryAndSubcategoryCreatedThenPersistBoth() {
        Category category = Category.builder()
                .name("Apparel and Accessories")
                .alias("Apparel and Accessories")
                .photo("Apparel and Accessories.jpg")
                .enabled(true)
                .build();
        Category categorySaved = categoryRepository.save(category);

        Category subCategory = Category.builder()
                .name("Computers and Electronics")
                .alias("Computers and Electronics")
                .photo("Computers and Electronics.jpg")
                .enabled(true)
                .parent(category)
                .build();

        Category subCategorySaved = categoryRepository.save(subCategory);

        assertThat(subCategorySaved.getParent().getId()).isEqualTo(categorySaved.getId());
    }

    @Test
    public void testFindCategoryByParentNull() {
        createCategories();
        List<Category> categories = categoryRepository.findByParentIsNull();
        categories.forEach(category -> System.out.println(category.getName()));
        assertThat(categories.isEmpty()).isFalse();
    }

    private List<Category> createCategories() {
        Category category1 = Category.builder().name("Phones & Tablets").enabled(true).build();
        Category category1_1 = Category.builder().name("Smartphones").enabled(true).parent(category1).build();
        Category category1_2 = Category.builder().name("Tablets").enabled(true).parent(category1).build();
        Category category1_3 = Category.builder().name("Phone Accessories").enabled(true).parent(category1).build();
        Category category1_4 = Category.builder().name("Tablet Accessories").enabled(true).parent(category1).build();
        Category category1_5 = Category.builder().name("Landline Phones").enabled(true).parent(category1).build();
        Category category1_6 = Category.builder().name("SIM Cards & Credit").enabled(true).parent(category1).build();

        Category category2 = Category.builder().name("Computing").enabled(true).build();
        Category category2_1 = Category.builder().name("Laptops").enabled(true).parent(category2).build();
        Category category2_2 = Category.builder().name("Desktops & Monitors").enabled(true).parent(category2).build();
        Category category2_3 = Category.builder().name("Computer Accessories").enabled(true).parent(category2).build();
        Category category2_4 = Category.builder().name("Printers, Scanners & Fax").enabled(true).parent(category2).build();

        Category category3 = Category.builder().name("Electronics").enabled(true).build();
        Category category3_1 = Category.builder().name("Televisions").enabled(true).parent(category3).build();
        Category category3_2 = Category.builder().name("Home Audio & Speakers").enabled(true).parent(category3).build();
        Category category3_3 = Category.builder().name("DVD Players & Recorders").enabled(true).parent(category3).build();
        Category category3_4 = Category.builder().name("Cameras & Camcorders").enabled(true).parent(category3).build();
        Category category3_5 = Category.builder().name("Gaming Consoles").enabled(true).parent(category3).build();

        Category category4 = Category.builder().name("Home & Office").enabled(true).build();
        Category category4_1 = Category.builder().name("Home & Kitchen Appliances").enabled(true).parent(category4).build();
        Category category4_2 = Category.builder().name("Bedding & Bath").enabled(true).parent(category4).build();
        Category category4_3 = Category.builder().name("Furniture & Decor").enabled(true).parent(category4).build();
        Category category4_4 = Category.builder().name("Office Supplies & Stationery").enabled(true).parent(category4).build();
        Category category4_5 = Category.builder().name("Lighting & Fixtures").enabled(true).parent(category4).build();
        Category category4_6 = Category.builder().name("Home Improvement & Tools").enabled(true).parent(category4).build();

        Category category5 = Category.builder().name("Health & Beauty").enabled(true).build();
        Category category5_1 = Category.builder().name("Fragrances").enabled(true).parent(category5).build();
        Category category5_2 = Category.builder().name("Makeup").enabled(true).parent(category5).build();
        Category category5_3 = Category.builder().name("Personal Care").enabled(true).parent(category5).build();
        Category category5_4 = Category.builder().name("Hair Care & Styling").enabled(true).parent(category5).build();
        Category category5_5 = Category.builder().name("Sexual Wellness").enabled(true).parent(category5).build();

        Category category6 = Category.builder().name("Fashion").enabled(true).build();
        Category category6_1 = Category.builder().name("Women's Fashion").enabled(true).parent(category6).build();
        Category category6_2 = Category.builder().name("Men's Fashion").enabled(true).parent(category6).build();
        Category category6_3 = Category.builder().name("Kids Fashion").enabled(true).parent(category6).build();
        Category category6_4 = Category.builder().name("Watches").enabled(true).parent(category6).build();
        Category category6_5 = Category.builder().name("Jewelry").enabled(true).parent(category6).build();
        Category category6_6 = Category.builder().name("Bags & Accessories").enabled(true).parent(category6).build();

        Category category7 = Category.builder().name("Supermarket").enabled(true).build();
        Category category7_1 = Category.builder().name("Food Cupboard").enabled(true).parent(category7).build();
        Category category7_2 = Category.builder().name("Beverages").enabled(true).parent(category7).build();
        Category category7_3 = Category.builder().name("Cleaning & Household").enabled(true).parent(category7).build();
        Category category7_4 = Category.builder().name("Beauty & Personal Care").enabled(true).parent(category7).build();
        Category category7_5 = Category.builder().name("All Baby Products").enabled(true).parent(category7).build();
        Category category7_6 = Category.builder().name("Pet Supplies").enabled(true).parent(category7).build();

        Category category8 = Category.builder().name("Sports & Fitness").enabled(true).build();
        Category category8_1 = Category.builder().name("Exercise & Fitness").enabled(true).parent(category8).build();
        Category category8_2 = Category.builder().name("Outdoor Recreation").enabled(true).parent(category8).build();
        Category category8_3 = Category.builder().name("Sports Apparel").enabled(true).parent(category8).build();
        Category category8_4 = Category.builder().name("Sports Shoes").enabled(true).parent(category8).build();
        Category category8_5 = Category.builder().name("Sports Accessories").enabled(true).parent(category8).build();

        Category category9 = Category.builder().name("Baby Products").enabled(true).build();
        Category category9_1 = Category.builder().name("Baby Gear").enabled(true).parent(category9).build();
        Category category9_2 = Category.builder().name("Diapers & Potty Training").enabled(true).parent(category9).build();
        Category category9_3 = Category.builder().name("Feeding").enabled(true).parent(category9).build();
        Category category9_4 = Category.builder().name("Baby & Toddler Toys").enabled(true).parent(category9).build();
        Category category9_5 = Category.builder().name("Baby Fashion").enabled(true).parent(category9).build();

        return categoryRepository.saveAll(Arrays.asList(category1, category1_1, category1_2, category1_3, category1_4, category1_5, category1_6, category2, category2_1, category2_2, category2_3, category2_4, category3, category3_1, category3_2, category3_3, category3_4, category3_5, category4, category4_1, category4_2, category4_3, category4_4, category4_5, category4_6, category5, category5_1, category5_2, category5_3, category5_4, category5_5, category6, category6_1, category6_2, category6_3, category6_4, category6_5, category6_6, category7, category7_1, category7_2, category7_3, category7_4, category7_5, category7_6, category8, category8_1, category8_2, category8_3, category8_4, category8_5, category9, category9_1, category9_2, category9_3, category9_4, category9_5));
    }


    @Test
    public void testPrintHierarchicalCategories() {
        createCategories();
        List<Category> rootCategories = categoryRepository.findByParentIsNull();
        rootCategories.forEach(category -> printCategories(category, "-"));
    }

    public void printCategory(Category category, int level) {
        // print the category name with appropriate indentation
        System.out.printf("%" + (level * 3 + 1) + "s- %s\n", "", category.getName());

        List<Category> childCategories = categoryRepository.findByParentId(category.getId());

        // recursively print the children of this category
        if (childCategories != null) {
            for (Category child : childCategories) {
                printCategory(child, level + 1);
            }
        }

    }

    public void printCategories(Category category, String prefix) {
        System.out.println(prefix + category.getName());

        List<Category> children = categoryRepository.findByParentId(category.getId());

        for (Category child : children) {
            printCategories(child, prefix + "-");
        }
    }
}